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
package org.apache.camel.component.google.pubsub.unit;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.google.pubsub.GooglePubsubConstants;
import org.apache.camel.component.google.pubsub.GooglePubsubEndpoint;
import org.apache.camel.component.google.pubsub.PubsubTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PubsubEndpointTest extends PubsubTestSupport {

    private static final String TEST_SUBSCRIPTION_NAME = "test-sub-name";

    // For testing purposes the URI params need to be aligned in alphabetical
    // order
    private static final String SUBSCRIPTION_URI = TEST_SUBSCRIPTION_NAME + "?ackMode=NONE" + "&concurrentConsumers=5";

    @EndpointInject("google-pubsub://{{project.id}}:" + SUBSCRIPTION_URI)
    private Endpoint from;

    @EndpointInject("direct:to")
    private Endpoint to;

    @Test
    public void testEndpointConfiguration() {

        // :1 identifies the first registered endpoint fo a type in the context
        Endpoint endpoint = context.hasEndpoint(String.format("google-pubsub:%s:%s", PROJECT_ID, SUBSCRIPTION_URI));
        assertNotNull(endpoint,
                String.format("Endpoint 'google-pubsub:%s:%s' is not found in Camel Context", PROJECT_ID, SUBSCRIPTION_URI));

        assertTrue(endpoint instanceof GooglePubsubEndpoint);
        GooglePubsubEndpoint pubsubEndpoint = (GooglePubsubEndpoint) endpoint;

        assertEquals(ExchangePattern.InOnly, pubsubEndpoint.createExchange().getPattern());
        assertEquals("google-pubsub://" + PROJECT_ID + ":" + SUBSCRIPTION_URI, pubsubEndpoint.getEndpointUri());

        assertEquals(PROJECT_ID, pubsubEndpoint.getProjectId());
        assertEquals(TEST_SUBSCRIPTION_NAME, pubsubEndpoint.getDestinationName());
        assertEquals(Integer.valueOf(5), pubsubEndpoint.getConcurrentConsumers());
        assertEquals(GooglePubsubConstants.AckMode.NONE, pubsubEndpoint.getAckMode());

    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from(from).to(to);
            }
        };
    }
}
