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
package org.apache.camel.spring.xml;

import java.util.List;

import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilderTest;
import org.apache.camel.spring.SpringCamelContext;
import org.junit.jupiter.api.Disabled;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A test case of the builder using Spring 2.0 to load the rules
 */
public class SpringXmlRouteBuilderTest extends RouteBuilderTest {
    protected AbstractXmlApplicationContext applicationContext;

    @Override
    protected List<Route> buildSimpleRoute() {
        return getRoutesFromContext("org/apache/camel/spring/xml/buildSimpleRoute.xml");
    }

    @Override
    protected List<Route> buildCustomProcessor() {
        List<Route> answer = getRoutesFromContext("org/apache/camel/spring/xml/buildCustomProcessor.xml");
        myProcessor = applicationContext.getBean("myProcessor", Processor.class);
        return answer;
    }

    @Override
    protected List<Route> buildCustomProcessorWithFilter() {
        List<Route> answer = getRoutesFromContext("org/apache/camel/spring/xml/buildCustomProcessorWithFilter.xml");
        myProcessor = applicationContext.getBean("myProcessor", Processor.class);
        return answer;
    }

    @Override
    protected List<Route> buildSimpleRouteWithHeaderPredicate() {
        return getRoutesFromContext("org/apache/camel/spring/xml/buildSimpleRouteWithHeaderPredicate.xml");
    }

    @Override
    protected List<Route> buildSimpleRouteWithChoice() {
        return getRoutesFromContext("org/apache/camel/spring/xml/buildSimpleRouteWithChoice.xml");
    }

    @Override
    protected List<Route> buildWireTap() {
        return getRoutesFromContext("org/apache/camel/spring/xml/buildWireTap.xml");
    }

    @Override
    protected List<Route> buildDynamicRecipientList() {
        return getRoutesFromContext("org/apache/camel/spring/xml/buildDynamicRecipientList.xml");
    }

    @Override
    protected List<Route> buildStaticRecipientList() {
        return getRoutesFromContext("org/apache/camel/spring/xml/buildStaticRecipientList.xml");
    }

    @Override
    protected List<Route> buildSplitter() {
        return getRoutesFromContext("org/apache/camel/spring/xml/buildSplitter.xml");
    }

    @Override
    protected List<Route> buildIdempotentConsumer() {
        return getRoutesFromContext("org/apache/camel/spring/xml/buildIdempotentConsumer.xml");
    }

    @Override
    @Disabled("This is tested elsewhere and not applicable in this scenario")
    public void testIdempotentConsumer() {
        // is tested elsewhere
    }

    @Override
    @Disabled("This is tested elsewhere and not applicable in this scenario")
    public void testRouteWithInterceptor() {
        // is tested elsewhere
    }

    protected List<Route> getRoutesFromContext(String classpathConfigFile) {
        applicationContext = new ClassPathXmlApplicationContext(classpathConfigFile);
        SpringCamelContext context = applicationContext.getBeansOfType(SpringCamelContext.class).values().iterator().next();
        assertNotNull(context, "No Camel Context in file: " + classpathConfigFile);
        List<Route> routes = context.getRoutes();
        assertNotNull(routes, "No routes available for context: " + context.getName() + " in file: " + classpathConfigFile);
        return routes;
    }
}
