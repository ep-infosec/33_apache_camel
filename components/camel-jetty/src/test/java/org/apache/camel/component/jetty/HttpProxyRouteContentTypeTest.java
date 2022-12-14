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
import org.apache.camel.support.ExchangeHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpProxyRouteContentTypeTest extends BaseJettyTest {

    @Test
    public void testHttpProxyWithContentType() {

        String out = template.requestBodyAndHeader("http://localhost:{{port}}/hello", "test", "Content-Type", "application/xml",
                String.class);

        assertEquals("application/xml", out, "Get a wrong response ");
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("jetty://http://localhost:{{port}}/hello")
                        .to("http://localhost:{{port}}/bye?throwExceptionOnFailure=false&bridgeEndpoint=true");

                from("jetty://http://localhost:{{port}}/bye").process(new Processor() {

                    public void process(Exchange exchange) {

                        exchange.getMessage().setBody(ExchangeHelper.getContentType(exchange));
                    }

                });
            }
        };
    }

}
