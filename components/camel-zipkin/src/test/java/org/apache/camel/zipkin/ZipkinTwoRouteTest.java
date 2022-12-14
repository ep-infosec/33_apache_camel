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
package org.apache.camel.zipkin;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import zipkin2.reporter.Reporter;

public class ZipkinTwoRouteTest extends CamelTestSupport {

    private ZipkinTracer zipkin;

    protected void setSpanReporter(ZipkinTracer zipkin) {
        zipkin.setSpanReporter(Reporter.NOOP);
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();

        zipkin = new ZipkinTracer();
        // we have 2 routes as services
        zipkin.addClientServiceMapping("seda:cat", "cat");
        zipkin.addServerServiceMapping("seda:cat", "cat");
        zipkin.addClientServiceMapping("seda:dog", "dog");
        zipkin.addServerServiceMapping("seda:dog", "dog");
        // capture message body as well
        zipkin.setIncludeMessageBody(true);
        setSpanReporter(zipkin);

        // attaching ourself to CamelContext
        zipkin.init(context);

        return context;
    }

    @Test
    public void testZipkinRoute() {
        template.requestBody("direct:start", "Camel say hello Cat");
    }

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:start").to("seda:cat");

                from("seda:cat").routeId("cat")
                        .log("routing at ${routeId}")
                        .delay(simple("${random(1000,2000)}"))
                        .setBody().constant("Cat says hello Dog")
                        .to("seda:dog");

                from("seda:dog").routeId("dog")
                        .log("routing at ${routeId}")
                        .delay(simple("${random(0,500)}"))
                        .setBody().constant("Dog say hello Cat and Camel");
            }
        };
    }
}
