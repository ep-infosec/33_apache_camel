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

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HttpBindingMapHttpMessageFormUrlEncodedFalseBodyTest extends BaseJettyTest {

    @Test
    public void testSendToJetty() {
        assertDoesNotThrow(() -> doSendToJetty());
    }

    private void doSendToJetty() {
        Map<String, Object> map = new HashMap<>();
        map.put("content-type", "application/x-www-form-urlencoded");
        map.put(Exchange.HTTP_METHOD, HttpMethods.POST);
        template.requestBodyAndHeaders("http://localhost:{{port}}/myapp/myservice?query1=a&query2=b", "b1=x&b2=y", map);
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("jetty:http://localhost:{{port}}/myapp/myservice?mapHttpMessageFormUrlEncodedBody=false")
                        .process(new Processor() {
                            public void process(Exchange exchange) {
                                String body = exchange.getIn().getBody(String.class);

                                // for unit testing make sure we got right message
                                assertEquals("b1=x&b2=y", body, "The body message is wrong");
                                assertEquals("a", exchange.getIn().getHeader("query1"),
                                        "Get a wrong query parameter from the message header");
                                assertEquals("b", exchange.getIn().getHeader("query2"),
                                        "Get a wrong query parameter from the message header");
                                assertNotEquals("x", exchange.getIn().getHeader("b1"),
                                        "Get a wrong form parameter from the message header");
                                assertNotEquals("y", exchange.getIn().getHeader("b2"),
                                        "Get a wrong form parameter from the message header");

                                // send a response
                                exchange.getMessage().setBody("Request message is OK");
                            }
                        });
            }
        };
    }

}
