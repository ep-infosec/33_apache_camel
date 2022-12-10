## ------------------------------------------------------------------------
## Licensed to the Apache Software Foundation (ASF) under one or more
## contributor license agreements.  See the NOTICE file distributed with
## this work for additional information regarding copyright ownership.
## The ASF licenses this file to You under the Apache License, Version 2.0
## (the "License"); you may not use this file except in compliance with
## the License.  You may obtain a copy of the License at
##
## http://www.apache.org/licenses/LICENSE-2.0
##
## Unless required by applicable law or agreed to in writing, software
## distributed under the License is distributed on an "AS IS" BASIS,
## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
## See the License for the specific language governing permissions and
## limitations under the License.
## ------------------------------------------------------------------------
package ${package}.internal;

import org.apache.camel.CamelContext;
import org.apache.camel.support.component.ApiMethodPropertiesHelper;

import ${package}.${name}Configuration;

/**
 * Singleton {@link ApiMethodPropertiesHelper} for ${name} component.
 */
public final class ${name}PropertiesHelper extends ApiMethodPropertiesHelper<${name}Configuration> {

    private static ${name}PropertiesHelper helper;

    private ${name}PropertiesHelper(CamelContext context) {
        super(context, ${name}Configuration.class, ${name}Constants.PROPERTY_PREFIX);
    }

    public static synchronized ${name}PropertiesHelper getHelper(CamelContext context) {
        if (helper == null) {
            helper = new ${name}PropertiesHelper(context);
        }
        return helper;
    }
}
