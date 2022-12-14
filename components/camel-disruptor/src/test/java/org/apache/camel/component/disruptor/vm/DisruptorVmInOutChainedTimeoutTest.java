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

import org.apache.camel.CamelExecutionException;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.StopWatch;
import org.junit.jupiter.api.Test;

import static org.apache.camel.test.junit5.TestSupport.assertIsInstanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class DisruptorVmInOutChainedTimeoutTest extends AbstractVmTestSupport {

    @Test
    void testDisruptorVmInOutChainedTimeout() {
        StopWatch watch = new StopWatch();

        try {
            template2.requestBody("disruptor-vm:a?timeout=1000", "Hello World");
            fail("Should have thrown an exception");
        } catch (CamelExecutionException e) {
            // the chained vm caused the timeout
            ExchangeTimedOutException cause = assertIsInstanceOf(ExchangeTimedOutException.class,
                    e.getCause());
            assertEquals(200, cause.getTimeout());
        }

        long delta = watch.taken();

        assertTrue(delta < 1100, "Should be faster than 1 sec, was: " + delta);
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("disruptor-vm:b")
                        .to("mock:b")
                        .delay(500)
                        .transform().constant("Bye World");
            }
        };
    }

    @Override
    protected RouteBuilder createRouteBuilderForSecondContext() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                errorHandler(noErrorHandler());

                from("disruptor-vm:a")
                        .to("mock:a")
                        // this timeout will trigger an exception to occur
                        .to("disruptor-vm:b?timeout=200")
                        .to("mock:a2");
            }
        };
    }
}
