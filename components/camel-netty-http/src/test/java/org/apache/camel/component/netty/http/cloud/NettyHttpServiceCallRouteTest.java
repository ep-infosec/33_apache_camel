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
package org.apache.camel.component.netty.http.cloud;

import org.apache.camel.ResolveEndpointFailedException;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.AvailablePortFinder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NettyHttpServiceCallRouteTest extends CamelTestSupport {

    @RegisterExtension
    AvailablePortFinder.Port port1 = AvailablePortFinder.find();

    @RegisterExtension
    AvailablePortFinder.Port port2 = AvailablePortFinder.find();

    @Test
    public void testCustomCall() {
        assertEquals("8081", template.requestBody("direct:custom", "hello", String.class));
        assertEquals("8082", template.requestBody("direct:custom", "hello", String.class));
    }

    @Test
    public void testDefaultSchema() {
        try {
            assertEquals("8081", template.requestBody("direct:default", "hello", String.class));
        } catch (RuntimeCamelException e) {
            assertTrue(e.getCause() instanceof ResolveEndpointFailedException);
        }
    }

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:custom")
                        .serviceCall()
                        .name("myService")
                        .component("netty-http")
                        .staticServiceDiscovery()
                        .servers("myService@localhost:" + port1)
                        .servers("myService@localhost:" + port2)
                        .endParent();

                from("direct:default")
                        .serviceCall()
                        .name("myService")
                        .staticServiceDiscovery()
                        .servers("myService@localhost:" + port1)
                        .servers("myService@localhost:" + port2)
                        .endParent();

                from("netty-http:http://localhost:" + port1)
                        .transform().constant("8081");
                from("netty-http:http://localhost:" + port2)
                        .transform().constant("8082");
            }
        };
    }
}
