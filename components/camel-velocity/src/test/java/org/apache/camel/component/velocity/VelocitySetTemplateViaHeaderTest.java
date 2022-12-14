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
package org.apache.camel.component.velocity;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class VelocitySetTemplateViaHeaderTest extends CamelTestSupport {

    private Exchange createLetter(String template) {
        Exchange exchange = context.getEndpoint("direct:a").createExchange();
        Message msg = exchange.getIn();
        msg.setHeader("firstName", "Claus");
        msg.setHeader("lastName", "Ibsen");
        msg.setHeader("item", "Camel in Action");
        msg.setHeader(VelocityConstants.VELOCITY_RESOURCE_URI, template);
        msg.setBody("PS: Next beer is on me, James");
        return exchange;
    }

    @Test
    public void testVelocityLetter() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);
        mock.message(0).body(String.class).contains("Thanks for the order of Camel in Action");

        template.send("direct:a", createLetter("org/apache/camel/component/velocity/letter.vm"));

        mock.assertIsSatisfied();

        mock.reset();

        mock.expectedMessageCount(1);
        mock.message(0).body(String.class).contains("Regards Apache Camel Riders Bookstore");

        template.send("direct:a", createLetter("org/apache/camel/component/velocity/letter2.vm"));

        mock.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:a").to("velocity:dummy?allowTemplateFromHeader=true").to("mock:result");
            }
        };
    }
}
