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
package org.apache.camel.component.disruptor;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.apache.camel.test.junit5.TestSupport.assertIsInstanceOf;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

public class DisruptorNoConsumerTest extends CamelTestSupport {
    @Test
    void testInOnly() {
        // no problem for in only as we do not expect a reply
        assertDoesNotThrow(() -> template.sendBody("direct:start", "Hello World"));
    }

    @Test
    void testInOut() {
        try {
            template.requestBody("direct:start", "Hello World");
            fail("Should throw an exception");
        } catch (CamelExecutionException e) {
            assertIsInstanceOf(ExchangeTimedOutException.class, e.getCause());
        }
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:start").to("disruptor:foo?timeout=1000");
            }
        };
    }
}
