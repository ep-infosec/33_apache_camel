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
import org.apache.camel.test.junit5.TestSupport;
import org.junit.jupiter.api.Test;

public class DisruptorVmInOnlyChainedTest extends AbstractVmTestSupport {

    @Test
    void testInOnlyDisruptorVmChained() throws Exception {
        getMockEndpoint("mock:a").expectedBodiesReceived("start");

        TestSupport.resolveMandatoryEndpoint(context2, "mock:b", MockEndpoint.class).expectedBodiesReceived("start-a");
        getMockEndpoint("mock:c").expectedBodiesReceived("start-a-b");

        template.sendBody("disruptor-vm:a", "start");

        MockEndpoint.assertIsSatisfied(context);
        MockEndpoint.assertIsSatisfied(context2);
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("disruptor-vm:a").to("mock:a").setBody(simple("${body}-a")).to("disruptor-vm:b");

                from("disruptor-vm:c").to("mock:c").setBody(simple("${body}-c"));
            }
        };
    }

    @Override
    protected RouteBuilder createRouteBuilderForSecondContext() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("disruptor-vm:b").to("mock:b").setBody(simple("${body}-b")).to("disruptor-vm:c");
            }
        };
    }
}
