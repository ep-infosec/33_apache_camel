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
package org.apache.camel.component.disruptor.vm;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;

public class DisruptorVmQueueTest extends AbstractVmTestSupport {

    @Test
    void testQueue() {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceivedInAnyOrder("Hello World", "Bye World", "Goodday World", "Bar");

        template2.sendBody("disruptor-vm:foo", "Hello World");
        template2.sendBody("disruptor-vm:foo?size=20", "Bye World");
        template2.sendBody("disruptor-vm:foo?concurrentConsumers=5", "Goodday World");
        template.sendBody("disruptor-vm:bar", "Bar");
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("disruptor-vm:bar").to("mock:result");
            }
        };
    }

    @Override
    protected RouteBuilder createRouteBuilderForSecondContext() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("disruptor-vm:foo?size=20&concurrentConsumers=2").to("mock:result");
            }
        };
    }
}
