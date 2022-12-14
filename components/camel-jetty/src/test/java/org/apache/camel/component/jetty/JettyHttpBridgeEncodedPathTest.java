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
package org.apache.camel.component.jetty;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JettyHttpBridgeEncodedPathTest extends BaseJettyTest {

    @Test
    public void testJettyHttpClient() {
        String response = template.requestBody("http://localhost:" + port2 + "/jettyTestRouteA?param1=%2B447777111222", null,
                String.class);
        assertEquals("param1=+447777111222", response, "Get a wrong response");
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                errorHandler(noErrorHandler());

                Processor serviceProc = new Processor() {
                    public void process(Exchange exchange) {
                        // %2B becomes decoded to a space
                        Object s = exchange.getIn().getHeader("param1");
                        // can be either + or %2B
                        assertTrue(s.equals(" 447777111222") || s.equals("%20447777111222") || s.equals("+447777111222")
                                || s.equals("%2B447777111222"));

                        // send back the query
                        exchange.getMessage().setBody(exchange.getIn().getHeader(Exchange.HTTP_QUERY));
                    }
                };
                from("jetty://http://localhost:" + port2 + "/jettyTestRouteA?matchOnUriPrefix=true")
                        .log("Using JettyTestRouteA route: CamelHttpPath=[${header.CamelHttpPath}], CamelHttpUri=[${header.CamelHttpUri}]")
                        .to("http://localhost:" + port1 + "/jettyTestRouteB?throwExceptionOnFailure=false&bridgeEndpoint=true");

                from("jetty://http://localhost:" + port1 + "/jettyTestRouteB?matchOnUriPrefix=true")
                        .log("Using JettyTestRouteB route: CamelHttpPath=[${header.CamelHttpPath}], CamelHttpUri=[${header.CamelHttpUri}]")
                        .process(serviceProc);
            }
        };
    }

}
