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
package org.apache.camel.component.jetty.javabody;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.component.jetty.BaseJettyTest;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.http.common.HttpCommonComponent;
import org.apache.camel.http.common.HttpConstants;
import org.junit.jupiter.api.Test;

import static org.apache.camel.test.junit5.TestSupport.assertIsInstanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class HttpJavaBodyTest extends BaseJettyTest {

    @Override
    public boolean isUseRouteBuilder() {
        return false;
    }

    @Test
    public void testHttpSendJavaBodyAndReceiveString() throws Exception {
        HttpCommonComponent jetty = context.getComponent("jetty", HttpCommonComponent.class);
        jetty.setAllowJavaSerializedObject(true);

        HttpComponent http = context.getComponent("http", HttpComponent.class);
        http.setAllowJavaSerializedObject(true);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("jetty:http://localhost:{{port}}/myapp/myservice").process(new Processor() {
                    public void process(Exchange exchange) {
                        MyCoolBean cool = exchange.getIn().getBody(MyCoolBean.class);
                        assertNotNull(cool);

                        assertEquals(123, cool.getId());
                        assertEquals("Camel", cool.getName());

                        // we send back plain test
                        exchange.getMessage().setHeader(Exchange.CONTENT_TYPE, "text/plain");
                        exchange.getMessage().setBody("OK");
                    }
                });
            }
        });
        context.start();

        MyCoolBean cool = new MyCoolBean(123, "Camel");

        String reply = template.requestBodyAndHeader("http://localhost:{{port}}/myapp/myservice", cool, Exchange.CONTENT_TYPE,
                HttpConstants.CONTENT_TYPE_JAVA_SERIALIZED_OBJECT,
                String.class);

        assertEquals("OK", reply);
    }

    @Test
    public void testHttpSendJavaBodyAndReceiveJavaBody() throws Exception {
        HttpCommonComponent jetty = context.getComponent("jetty", HttpCommonComponent.class);
        jetty.setAllowJavaSerializedObject(true);

        HttpComponent http = context.getComponent("http", HttpComponent.class);
        http.setAllowJavaSerializedObject(true);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("jetty:http://localhost:{{port}}/myapp/myservice").process(new Processor() {
                    public void process(Exchange exchange) {
                        MyCoolBean cool = exchange.getIn().getBody(MyCoolBean.class);
                        assertNotNull(cool);

                        assertEquals(123, cool.getId());
                        assertEquals("Camel", cool.getName());

                        MyCoolBean reply = new MyCoolBean(456, "Camel rocks");
                        exchange.getMessage().setBody(reply);
                        exchange.getMessage().setHeader(Exchange.CONTENT_TYPE,
                                HttpConstants.CONTENT_TYPE_JAVA_SERIALIZED_OBJECT);
                    }
                });
            }
        });
        context.start();

        MyCoolBean cool = new MyCoolBean(123, "Camel");

        MyCoolBean reply
                = template.requestBodyAndHeader("http://localhost:{{port}}/myapp/myservice", cool, Exchange.CONTENT_TYPE,
                        HttpConstants.CONTENT_TYPE_JAVA_SERIALIZED_OBJECT, MyCoolBean.class);

        assertEquals(456, reply.getId());
        assertEquals("Camel rocks", reply.getName());
    }

    @Test
    public void testHttpSendStringAndReceiveJavaBody() throws Exception {
        HttpCommonComponent jetty = context.getComponent("jetty", HttpCommonComponent.class);
        jetty.setAllowJavaSerializedObject(true);

        HttpComponent http = context.getComponent("http", HttpComponent.class);
        http.setAllowJavaSerializedObject(true);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("jetty:http://localhost:{{port}}/myapp/myservice").process(new Processor() {
                    public void process(Exchange exchange) {
                        String body = exchange.getIn().getBody(String.class);
                        assertNotNull(body);
                        assertEquals("Hello World", body);

                        MyCoolBean reply = new MyCoolBean(456, "Camel rocks");
                        exchange.getMessage().setBody(reply);
                        exchange.getMessage().setHeader(Exchange.CONTENT_TYPE,
                                HttpConstants.CONTENT_TYPE_JAVA_SERIALIZED_OBJECT);
                    }
                });
            }
        });
        context.start();

        MyCoolBean reply = template.requestBody("http://localhost:{{port}}/myapp/myservice", "Hello World", MyCoolBean.class);

        assertEquals(456, reply.getId());
        assertEquals("Camel rocks", reply.getName());
    }

    @Test
    public void testNotAllowedReceive() throws Exception {
        HttpCommonComponent jetty = context.getComponent("jetty", HttpCommonComponent.class);
        jetty.setAllowJavaSerializedObject(false);

        HttpComponent http = context.getComponent("http", HttpComponent.class);
        http.setAllowJavaSerializedObject(true);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                onException(Exception.class).to("mock:error");

                from("jetty:http://localhost:{{port}}/myapp/myservice").process(new Processor() {
                    public void process(Exchange exchange) {
                        String body = exchange.getIn().getBody(String.class);
                        assertNotNull(body);
                        assertEquals("Hello World", body);

                        MyCoolBean reply = new MyCoolBean(456, "Camel rocks");
                        exchange.getMessage().setBody(reply);
                        exchange.getMessage().setHeader(Exchange.CONTENT_TYPE,
                                HttpConstants.CONTENT_TYPE_JAVA_SERIALIZED_OBJECT);
                    }
                });
            }
        });
        context.start();

        try {
            template.requestBody("http://localhost:{{port}}/myapp/myservice", "Hello World", MyCoolBean.class);
            fail("Should fail");
        } catch (Exception e) {
            // expected
        }
    }

    @Test
    public void testNotAllowed() throws Exception {
        HttpCommonComponent jetty = context.getComponent("jetty", HttpCommonComponent.class);
        jetty.setAllowJavaSerializedObject(false);

        HttpComponent http = context.getComponent("http", HttpComponent.class);
        http.setAllowJavaSerializedObject(true);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("jetty:http://localhost:{{port}}/myapp/myservice").process(new Processor() {
                    public void process(Exchange exchange) {
                        String body = exchange.getIn().getBody(String.class);
                        assertNotNull(body);
                        assertEquals("Hello World", body);

                        MyCoolBean reply = new MyCoolBean(456, "Camel rocks");
                        exchange.getMessage().setBody(reply);
                        exchange.getMessage().setHeader(Exchange.CONTENT_TYPE,
                                HttpConstants.CONTENT_TYPE_JAVA_SERIALIZED_OBJECT);
                    }
                });
            }
        });
        context.start();

        MyCoolBean cool = new MyCoolBean(123, "Camel");

        try {
            template.requestBodyAndHeader("http://localhost:{{port}}/myapp/myservice", cool, Exchange.CONTENT_TYPE,
                    HttpConstants.CONTENT_TYPE_JAVA_SERIALIZED_OBJECT,
                    MyCoolBean.class);
            fail("Should fail");
        } catch (CamelExecutionException e) {
            HttpOperationFailedException cause = assertIsInstanceOf(HttpOperationFailedException.class, e.getCause());
            assertEquals(415, cause.getStatusCode());
        }
    }

}
