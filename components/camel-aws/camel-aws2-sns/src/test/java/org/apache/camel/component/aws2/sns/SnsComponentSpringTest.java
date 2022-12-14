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

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnsComponentSpringTest extends CamelSpringTestSupport {

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
    protected ClassPathXmlApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("org/apache/camel/component/aws2/sns/SnsComponentSpringTest-context.xml");
    }
}
