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
package org.apache.camel.spring.interceptor;

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.processor.async.MyAsyncComponent;
import org.apache.camel.spring.spi.LegacyTransactionErrorHandlerBuilder;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test to demonstrate the transactional client pattern.
 */
public class TransactionalClientDataSourceAsyncTest extends TransactionalClientDataSourceTest {

    @Override
    @Test
    public void testTransactionRollback() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:error");
        mock.expectedMessageCount(1);

        try {
            template.sendBody("direct:fail", "Hello World");
            fail("Should have thrown exception");
        } catch (RuntimeCamelException e) {
            // expected as we fail
            assertIsInstanceOf(RuntimeCamelException.class, e.getCause());
            assertTrue(e.getCause().getCause() instanceof IllegalArgumentException);
            assertEquals("We don't have Donkeys, only Camels", e.getCause().getCause().getMessage());
        }

        assertMockEndpointsSatisfied();

        int count = jdbc.queryForObject("select count(*) from books", Integer.class);
        assertEquals(1, count, "Number of books");
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() throws Exception {

                context.addComponent("async", new MyAsyncComponent());

                // use required as transaction policy
                SpringTransactionPolicy required
                        = context.getRegistry().lookupByNameAndType("PROPAGATION_REQUIRED", SpringTransactionPolicy.class);

                // configure to use transaction error handler and pass on the required as it will fetch
                // the transaction manager from it that it needs
                LegacyTransactionErrorHandlerBuilder teh = new LegacyTransactionErrorHandlerBuilder();
                teh.setSpringTransactionPolicy(required);
                errorHandler(teh);

                // on exception is also supported
                onException(IllegalArgumentException.class).handled(false).to("mock:error");

                from("direct:okay")
                        .policy(required)
                        .setBody(constant("Tiger in Action")).bean("bookService")
                        .log("Before thread ${threadName}")
                        .to("async:bye:camel")
                        .log("After thread ${threadName}")
                        .setBody(constant("Elephant in Action")).bean("bookService");

                from("direct:fail")
                        .policy(required)
                        .setBody(constant("Tiger in Action")).bean("bookService")
                        .log("Before thread ${threadName}")
                        .to("async:bye:camel")
                        .log("After thread ${threadName}")
                        .setBody(constant("Donkey in Action")).bean("bookService");
            }
        };
    }

}
