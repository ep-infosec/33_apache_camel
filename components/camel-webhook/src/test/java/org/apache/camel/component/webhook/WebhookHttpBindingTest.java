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
package org.apache.camel.component.webhook;

import java.util.Arrays;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.webhook.support.TestComponent;
import org.apache.camel.spi.Registry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WebhookHttpBindingTest extends WebhookTestBase {

    @Test
    public void testWrapper() {
        String result = template.requestBody("netty-http:http://localhost:" + port
                                             + WebhookConfiguration.computeDefaultPath("wb-delegate://xx"),
                "", String.class);
        assertEquals("msg: webhook", result);

        result = template.requestBodyAndHeader("netty-http:http://localhost:" + port
                                               + WebhookConfiguration.computeDefaultPath("wb-delegate://xx"),
                "", Exchange.HTTP_METHOD, "PUT", String.class);
        assertEquals("msg: webhook", result);
    }

    @Test
    public void testGetError() {
        assertThrows(CamelExecutionException.class,
                () -> template.requestBodyAndHeader("netty-http:http://localhost:" + port, "",
                        Exchange.HTTP_METHOD, "GET", String.class));
    }

    @Override
    protected void bindToRegistry(Registry registry) {
        registry.bind("wb-delegate-component", new TestComponent(endpoint -> {
            endpoint.setWebhookHandler(proc -> ex -> {
                ex.getMessage().setBody("webhook");
                proc.process(ex);
            });
            endpoint.setWebhookMethods(() -> Arrays.asList("POST", "PUT"));
        }));
    }

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {

                restConfiguration()
                        .host("0.0.0.0")
                        .port(port);

                from("webhook:wb-delegate://xx")
                        .transform(body().prepend("msg: "));

            }
        };
    }
}
