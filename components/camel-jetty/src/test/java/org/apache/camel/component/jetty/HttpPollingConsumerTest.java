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
package org.apache.camel.component.jetty;

import java.net.SocketTimeoutException;

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;

import static org.apache.camel.test.junit5.TestSupport.assertIsInstanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class HttpPollingConsumerTest extends BaseJettyTest {

    @Test
    public void testReceive() {
        String body = consumer.receiveBody("http://localhost:{{port}}/test", String.class);
        assertEquals("Bye World", body);
    }

    @Test
    public void testReceiveTimeout() {
        String body = consumer.receiveBody("http://localhost:{{port}}/test", 5000, String.class);
        assertEquals("Bye World", body);
    }

    @Test
    public void testReceiveTimeoutTriggered() {
        try {
            consumer.receiveBody("http://localhost:{{port}}/test", 250, String.class);
            fail("Should have thrown an exception");
        } catch (RuntimeCamelException e) {
            assertIsInstanceOf(SocketTimeoutException.class, e.getCause());
        }
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("jetty://http://localhost:{{port}}/test").delay(2000).transform(constant("Bye World"));
            }
        };
    }

}
