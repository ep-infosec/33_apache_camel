/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.language.csimple.CSimpleCodeGenerator;
import org.apache.camel.language.csimple.CSimpleGeneratedCode;
import org.apache.camel.parser.RouteBuilderParser;
import org.apache.camel.parser.XmlRouteParser;
import org.apache.camel.parser.model.CamelCSimpleExpressionDetails;
import org.apache.camel.support.PatternHelper;
import org.apache.camel.tooling.util.FileUtil;
import org.apache.camel.util.IOHelper;
import org.apache.camel.util.StringHelper;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.mojo.exec.AbstractExecMojo;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Parses the source code and generates source code for the csimple language.
 */
@Mojo(name = "generate", threadSafe = true, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME,
      defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class GenerateMojo extends AbstractExecMojo {

    public static final String GENERATED_MSG = "Generated by camel build tools - do NOT edit this file!";
    public static final String RESOURCE_FILE = "META-INF/services/org/apache/camel/csimple.properties";

    /**
     * The maven project.
     */
    @Parameter(property = "project", required = true, readonly = true)
    protected MavenProject project;

    // Output directory

    /**
     * The output directory for generated source files
     */
    @Parameter(defaultValue = "${project.basedir}/src/generated/java")
    protected File outputDir;

    /**
     * The output directory for generated resources files
     */
    @Parameter(defaultValue = "${project.basedir}/src/generated/resources")
    protected File outputResourceDir;

    /**
     * The resources directory for configuration files
     */
    @Parameter(defaultValue = "${project.basedir}/src/main/resources")
    protected File resourceDir;

    /**
     * Whether to include Java files to be validated for invalid Camel endpoints
     */
    @Parameter(property = "camel.includeJava", defaultValue = "true")
    private boolean includeJava;

    /**
     * Whether to include XML files to be validated for invalid Camel endpoints
     */
    @Parameter(property = "camel.includeXml", defaultValue = "true")
    private boolean includeXml;

    /**
     * Whether to include test source code
     */
    @Parameter(property = "camel.includeTest", defaultValue = "false")
    private boolean includeTest;

    /**
     * To filter the names of java and xml files to only include files matching any of the given list of patterns
     * (wildcard and regular expression). Multiple values can be separated by comma.
     */
    @Parameter(property = "camel.includes")
    private String includes;

    /**
     * To filter the names of java and xml files to exclude files matching any of the given list of patterns (wildcard
     * and regular expression). Multiple values can be separated by comma.
     */
    @Parameter(property = "camel.excludes")
    private String excludes;

    private final Set<String> imports = new TreeSet<>();
    private final Map<String, String> aliases = new HashMap<>();

    // CHECKSTYLE:OFF
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        loadConfiguration();

        CSimpleCodeGenerator generator = new CSimpleCodeGenerator();
        generator.setAliases(aliases);
        generator.setImports(imports);

        doExecuteRoutes(generator);
    }

    protected void doExecuteRoutes(CSimpleCodeGenerator generator) {
        List<CamelCSimpleExpressionDetails> csimpleExpressions = new ArrayList<>();
        Set<File> javaFiles = new LinkedHashSet<>();
        Set<File> xmlFiles = new LinkedHashSet<>();

        // find all java route builder classes
        if (includeJava) {
            List list = project.getCompileSourceRoots();
            for (Object obj : list) {
                String dir = (String) obj;
                findJavaFiles(new File(dir), javaFiles);
            }
            if (includeTest) {
                list = project.getTestCompileSourceRoots();
                for (Object obj : list) {
                    String dir = (String) obj;
                    findJavaFiles(new File(dir), javaFiles);
                }
            }
        }
        // find all xml routes
        if (includeXml) {
            List list = project.getResources();
            for (Object obj : list) {
                Resource dir = (Resource) obj;
                findXmlFiles(new File(dir.getDirectory()), xmlFiles);
            }
            if (includeTest) {
                list = project.getTestResources();
                for (Object obj : list) {
                    Resource dir = (Resource) obj;
                    findXmlFiles(new File(dir.getDirectory()), xmlFiles);
                }
            }
        }

        for (File file : javaFiles) {
            if (matchRouteFile(file)) {
                try {
                    List<CamelCSimpleExpressionDetails> fileCSimpleExpressions = new ArrayList<>();

                    // parse the java source code and find Camel RouteBuilder classes
                    String fqn = file.getPath();
                    String baseDir = ".";
                    JavaType out = Roaster.parse(file);
                    // we should only parse java classes (not interfaces and enums etc)
                    if (out instanceof JavaClassSource) {
                        JavaClassSource clazz = (JavaClassSource) out;
                        RouteBuilderParser.parseRouteBuilderCSimpleExpressions(clazz, baseDir, fqn, fileCSimpleExpressions);
                        csimpleExpressions.addAll(fileCSimpleExpressions);
                    }
                } catch (Exception e) {
                    getLog().warn("Error parsing java file " + file + " code due " + e.getMessage(), e);
                }
            }
        }
        for (File file : xmlFiles) {
            if (matchRouteFile(file)) {
                try {
                    List<CamelCSimpleExpressionDetails> fileSimpleExpressions = new ArrayList<>();
                    // parse the xml source code and find Camel routes
                    String fqn = file.getPath();
                    String baseDir = ".";
                    InputStream is = new FileInputStream(file);
                    XmlRouteParser.parseXmlRouteCSimpleExpressions(is, baseDir, fqn, fileSimpleExpressions);
                    is.close();
                    csimpleExpressions.addAll(fileSimpleExpressions);
                } catch (Exception e) {
                    getLog().warn("Error parsing xml file " + file + " code due " + e.getMessage(), e);
                }
            }
        }


        if (!csimpleExpressions.isEmpty()) {
            getLog().info("Discovered " + csimpleExpressions.size() + " csimple expressions");

            final List<CSimpleGeneratedCode> classes = new ArrayList<>();

            for (CamelCSimpleExpressionDetails cs : csimpleExpressions) {
                String script = cs.getCsimple();
                String fqn = cs.getClassName();
                if (script != null && fqn == null) {
                    // its from XML file so use a pseduo fqn name instead
                    fqn = "org.apache.camel.language.csimple.XmlRouteBuilder";
                }
                if (script != null) {
                    CSimpleGeneratedCode code;
                    if (cs.isPredicate()) {
                        code = generator.generatePredicate(fqn, script);
                    } else {
                        code = generator.generateExpression(fqn, script);
                    }
                    classes.add(code);
                    if (getLog().isDebugEnabled()) {
                        getLog().debug("Generated source code:\n\n\n" + code.getCode() + "\n\n\n");
                    }
                    String fileName = code.getFqn().replace('.', '/') + ".java";
                    outputDir.mkdirs();
                    boolean saved = updateResource(outputDir.toPath().resolve(fileName), code.getCode());
                    if (saved) {
                        getLog().info("Generated csimple source code file: " + fileName);
                    }
                }
            }
            if (!classes.isEmpty()) {
                // generate .properties file
                StringWriter w = new StringWriter();
                w.append("# " + GENERATED_MSG + "\n");
                classes.forEach(c -> w.write(c.getFqn() + "\n"));
                String fileName = RESOURCE_FILE;
                outputResourceDir.mkdirs();
                boolean saved = updateResource(outputResourceDir.toPath().resolve(RESOURCE_FILE), w.toString());
                if (saved) {
                    getLog().info("Generated csimple resource file: " + fileName);
                }
            }
        }

    }

    private void loadConfiguration() {
        String configFile = resourceDir.getPath() + "/camel-csimple.properties";

        String loaded;
        InputStream is = null;
        try {
            // load from file system
            File file = new File(configFile);
            if (file.exists()) {
                is = new FileInputStream(file);
            }
            if (is == null) {
                return;
            }
            loaded = IOHelper.loadText(is);
        } catch (IOException e) {
            throw new RuntimeCamelException("Cannot load " + configFile);

        }
        IOHelper.close(is);

        int counter1 = 0;
        int counter2 = 0;
        String[] lines = loaded.split("\n");
        for (String line : lines) {
            line = line.trim();
            // skip comments
            if (line.startsWith("#")) {
                continue;
            }
            // imports
            if (line.startsWith("import ")) {
                imports.add(line);
                counter1++;
                continue;
            }
            // aliases as key=value
            String key = StringHelper.before(line, "=");
            String value = StringHelper.after(line, "=");
            if (key != null) {
                key = key.trim();
            }
            if (value != null) {
                value = value.trim();
            }
            if (key != null && value != null) {
                this.aliases.put(key, value);
                counter2++;
            }
        }
        if (counter1 > 0 || counter2 > 0) {
            getLog().info("Loaded csimple language imports: " + counter1 + " and aliases: " + counter2 + " from configuration: " + configFile);
        }
    }

    // CHECKSTYLE:ON

    private void findJavaFiles(File dir, Set<File> javaFiles) {
        File[] files = dir.isDirectory() ? dir.listFiles() : null;
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                } else if (file.isDirectory()) {
                    findJavaFiles(file, javaFiles);
                }
            }
        }
    }

    private void findXmlFiles(File dir, Set<File> xmlFiles) {
        File[] files = dir.isDirectory() ? dir.listFiles() : null;
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".xml")) {
                    xmlFiles.add(file);
                } else if (file.isDirectory()) {
                    findXmlFiles(file, xmlFiles);
                }
            }
        }
    }

    private boolean matchRouteFile(File file) {
        if (excludes == null && includes == null) {
            return true;
        }

        // exclude take precedence
        if (excludes != null) {
            for (String exclude : excludes.split(",")) {
                boolean match = isMatch(exclude, file);
                if (match) {
                    return false;
                }
            }
        }

        // include
        if (includes != null) {
            for (String include : includes.split(",")) {
                boolean match = isMatch(include, file);
                if (match) {
                    return true;
                }
            }
            // did not match any includes
            return false;
        }

        // was not excluded nor failed include so its accepted
        return true;
    }

    private boolean isMatch(String include, File file) {
        include = include.trim();
        // try both with and without directory in the name
        String fqn = stripRootPath(asRelativeFile(file.getAbsolutePath()));
        return PatternHelper.matchPattern(fqn, include) || PatternHelper.matchPattern(file.getName(), include);
    }

    private String asRelativeFile(String name) {
        String answer = name;

        String base = project.getBasedir().getAbsolutePath();
        if (name.startsWith(base)) {
            answer = name.substring(base.length());
            // skip leading slash for relative path
            if (answer.startsWith(File.separator)) {
                answer = answer.substring(1);
            }
        }
        return answer;
    }

    private String stripRootPath(String name) {
        // strip out any leading source / resource directory

        String compileSourceRoot = findInCompileSourceRoots(name);
        if (compileSourceRoot != null) {
            return compileSourceRoot;
        }

        String buildPath = findInResources(name);
        if (buildPath != null) {
            return buildPath;
        }

        return name;
    }

    private String findInCompileSourceRoots(String name) {
        String compileSourceRoot = findInCompileSourceRoots(project.getCompileSourceRoots(), name);
        if (compileSourceRoot != null) {
            return compileSourceRoot;
        }

        return findInCompileSourceRoots(project.getTestCompileSourceRoots(), name);
    }

    private String findInCompileSourceRoots(List<String> list, String name) {
        for (String dir : list) {
            dir = asRelativeFile(dir);
            if (name.startsWith(dir)) {
                return name.substring(dir.length() + 1);
            }
        }
        return null;
    }

    private String findInResources(String name) {
        String buildPath = findInResources(project.getResources(), name);
        if (buildPath != null) {
            return buildPath;
        }

        return findInResources(project.getTestResources(), name);
    }

    private String findInResources(List<Resource> resources, String name) {
        for (Resource resource : resources) {
            String dir = asRelativeFile(resource.getDirectory());
            if (name.startsWith(dir)) {
                return name.substring(dir.length() + 1);
            }
        }
        return null;
    }

    public static boolean updateResource(Path out, String data) {
        try {
            if (FileUtil.updateFile(out, data)) {
                return true;
            }
        } catch (IOException e) {
            throw new IOError(e);
        }
        return false;
    }

}
