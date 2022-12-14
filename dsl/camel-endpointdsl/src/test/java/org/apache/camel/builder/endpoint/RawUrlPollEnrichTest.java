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
package org.apache.camel.builder.endpoint;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;

public class RawUrlPollEnrichTest extends BaseEndpointDslTest {

    @Test
    public void testRawUrl() throws Exception {
        template.sendBody("seda:foo?size=1", "Camel");

        MockEndpoint mock = getMockEndpoint("mock:result");

        mock.expectedBodiesReceived("Hello Camel");

        template.sendBodyAndHeader("direct:start", "Hello", "size", "1");

        mock.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new EndpointRouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(direct("start"))
                        .pollEnrich(seda("foo").size("${header.size}"), new SampleAggregator())
                        .to(mock("result"));
            }
        };
    }

    private static class SampleAggregator implements AggregationStrategy {

        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            if (newExchange == null) {
                return oldExchange;
            }
            Object oldBody = oldExchange.getIn().getBody();
            Object newBody = newExchange.getIn().getBody();
            oldExchange.getIn().setBody(oldBody + " " + newBody);
            return oldExchange;
        }
    }
}
