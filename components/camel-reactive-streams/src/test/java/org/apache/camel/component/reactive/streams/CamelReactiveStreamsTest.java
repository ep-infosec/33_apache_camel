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
package org.apache.camel.component.reactive.streams;

import org.apache.camel.component.reactive.streams.api.CamelReactiveStreams;
import org.apache.camel.component.reactive.streams.api.CamelReactiveStreamsService;
import org.apache.camel.component.reactive.streams.engine.DefaultCamelReactiveStreamsService;
import org.apache.camel.component.reactive.streams.support.ReactiveStreamsTestService;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CamelReactiveStreamsTest {

    @Test
    public void testDefaultService() {
        DefaultCamelContext context = new DefaultCamelContext();
        try {
            context.start();

            CamelReactiveStreamsService service1 = CamelReactiveStreams.get(context);
            assertTrue(service1 instanceof DefaultCamelReactiveStreamsService);
        } finally {
            context.stop();
        }
    }

    @Test
    public void testSameDefaultServiceReturned() {
        DefaultCamelContext context = new DefaultCamelContext();
        try {
            context.start();

            CamelReactiveStreamsService service1 = CamelReactiveStreams.get(context);
            CamelReactiveStreamsService service2 = CamelReactiveStreams.get(context);

            assertTrue(service1 instanceof DefaultCamelReactiveStreamsService);
            assertEquals(service1, service2);
        } finally {
            context.stop();
        }
    }

    @Test
    public void testSameServiceReturnedFromRegistry() {
        ReactiveStreamsComponent component = new ReactiveStreamsComponent();

        SimpleRegistry registry = new SimpleRegistry();
        registry.bind("dummy", new ReactiveStreamsTestService("from-registry"));

        DefaultCamelContext context = new DefaultCamelContext(registry);
        context.addComponent(ReactiveStreamsConstants.SCHEME, component);

        try {
            context.start();

            CamelReactiveStreamsService service1 = CamelReactiveStreams.get(context);
            CamelReactiveStreamsService service2 = CamelReactiveStreams.get(context);

            assertEquals(service1, service2);
            assertTrue(service1 instanceof ReactiveStreamsTestService);
            assertEquals("from-registry", service1.getId());
        } finally {
            context.stop();
        }
    }

    @Test
    public void testNamedServiceResolvedUsingFactory() {
        ReactiveStreamsComponent component = new ReactiveStreamsComponent();
        component.setServiceType("test-service");

        DefaultCamelContext context = new DefaultCamelContext();
        context.addComponent(ReactiveStreamsConstants.SCHEME, component);

        try {
            context.start();

            CamelReactiveStreamsService service1 = CamelReactiveStreams.get(context);
            CamelReactiveStreamsService service2 = CamelReactiveStreams.get(context);

            assertEquals(service1, service2);
            assertTrue(service1 instanceof ReactiveStreamsTestService);
            assertEquals("test-service", service1.getId());
        } finally {
            context.stop();
        }
    }
}
