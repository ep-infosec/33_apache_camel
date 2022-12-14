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
package org.apache.camel.component.jetty.proxy;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jetty.BaseJettyTest;
import org.apache.camel.converter.stream.CachedOutputStream;
import org.apache.camel.util.IOHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JettyChuckedFalseTest extends BaseJettyTest {

    @Test
    public void runningTest() {
        Exchange exchange = template.request("http://localhost:{{port}}/test", new Processor() {
            @Override
            public void process(Exchange exchange) {
                // we do nothing here, so http producer will send a GET method
                // request
            }

        });
        Message out = exchange.getMessage();
        // make sure we have the content-length header
        String len = out.getHeader(Exchange.CONTENT_LENGTH, String.class);
        assertEquals("20", len, "We should have the content-length header here.");
        String response = out.getBody(String.class);
        assertEquals("This is hello world.", response, "Get a wrong response");
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {

                from("jetty:http://localhost:{{port}}/test?matchOnUriPrefix=true&chunked=false")
                        .to("http://localhost:{{port2}}/other?bridgeEndpoint=true");

                from("jetty:http://localhost:{{port2}}/other").process(new Processor() {

                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getMessage().setHeader(Exchange.CONTENT_TYPE, "image/jpeg");
                        CachedOutputStream stream = new CachedOutputStream(exchange);
                        stream.write("This is hello world.".getBytes());
                        exchange.getMessage().setBody(stream.getInputStream());
                        IOHelper.close(stream);
                    }
                });
            }
        };
    }

}
