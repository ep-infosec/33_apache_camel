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
package org.apache.camel.component.aws2.sns;

import org.apache.camel.BindToRegistry;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnsComponentTest extends CamelTestSupport {

    @BindToRegistry("amazonSNSClient")
    AmazonSNSClientMock client = new AmazonSNSClientMock();

    @Test
    public void sendInOnly() {
        Exchange exchange = template.send("direct:start", ExchangePattern.InOnly, new Processor() {
            public void process(Exchange exchange) {
                exchange.getIn().setHeader(Sns2Constants.SUBJECT, "This is my subject text.");
                exchange.getIn().setBody("This is my message text.");
            }
        });

        assertEquals("dcc8ce7a-7f18-4385-bedd-b97984b4363c", exchange.getIn().getHeader(Sns2Constants.MESSAGE_ID));
    }

    @Test
    public void sendInOut() {
        Exchange exchange = template.send("direct:start", ExchangePattern.InOut, new Processor() {
            public void process(Exchange exchange) {
                exchange.getIn().setHeader(Sns2Constants.SUBJECT, "This is my subject text.");
                exchange.getIn().setBody("This is my message text.");
            }
        });

        assertEquals("dcc8ce7a-7f18-4385-bedd-b97984b4363c", exchange.getMessage().getHeader(Sns2Constants.MESSAGE_ID));
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:start").to("aws2-sns://MyTopic?amazonSNSClient=#amazonSNSClient");
            }
        };
    }

    @DisplayName(value = "Test for CAMEL-16586")
    @Test
    public void createMultipleEndpoints() throws Exception {
        Sns2Component component = context.getComponent("aws2-sns", Sns2Component.class);

        Sns2Endpoint endpoint1 = (Sns2Endpoint) component.createEndpoint("aws2-sns://Topic1?accessKey=xxx&secretKey=yyy");
        assertEquals("Topic1", endpoint1.getConfiguration().getTopicName());

        Sns2Endpoint endpoint2 = (Sns2Endpoint) component.createEndpoint("aws2-sns://Topic2?accessKey=xxx&secretKey=yyy");
        assertEquals("Topic2", endpoint2.getConfiguration().getTopicName());

        // this now fails because endpoint1 and endpoint2 share the same config
        assertEquals("Topic1", endpoint1.getConfiguration().getTopicName());
    }
}
