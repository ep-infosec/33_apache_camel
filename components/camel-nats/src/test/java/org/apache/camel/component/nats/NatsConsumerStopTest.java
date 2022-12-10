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
package org.apache.camel.component.nats;

import org.apache.camel.Consumer;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test NATS consumer stop happens cleanly. See https://issues.apache.org/jira/browse/CAMEL-15834.
 */
public class NatsConsumerStopTest extends CamelTestSupport {
    @Test
    public void testConsumerStop() throws Exception {
        NatsEndpoint endpoint = context.getEndpoint("nats:test?flushConnection=true", NatsEndpoint.class);
        Consumer consumer = endpoint.createConsumer(null);
        assertDoesNotThrow(() -> consumer.stop());
    }
}
