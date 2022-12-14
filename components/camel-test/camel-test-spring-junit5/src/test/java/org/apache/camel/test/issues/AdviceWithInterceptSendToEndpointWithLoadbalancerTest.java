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
package org.apache.camel.test.issues;

import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AdviceWithInterceptSendToEndpointWithLoadbalancerTest extends CamelSpringTestSupport {

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext(
                "org/apache/camel/test/issues/AdviceWithInterceptSendToEndpointWithLoadbalancerTest.xml");
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Test
    public void testSimpleMultipleAdvice() throws Exception {

        AdviceWith.adviceWith(context, "load-balancer-test-route",
                a -> a.interceptSendToEndpoint("seda:end1").skipSendToOriginalEndpoint().to("mock:end"));

        context.start();

        getMockEndpoint("mock:end").expectedMessageCount(1);

        template.sendBody("direct:start", "Hello World");

        MockEndpoint.assertIsSatisfied(context);
    }

    public static class LoadbalancerTestRoute extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("direct:start")
                    .routeId("load-balancer-test-route")
                    .loadBalance().failover()
                    .to("seda:end1", "seda:end2");
        }
    }
}
