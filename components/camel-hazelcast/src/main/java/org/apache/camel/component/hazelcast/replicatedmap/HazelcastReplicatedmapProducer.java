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
package org.apache.camel.component.hazelcast.replicatedmap;

import java.util.Map;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.replicatedmap.ReplicatedMap;
import org.apache.camel.Exchange;
import org.apache.camel.component.hazelcast.HazelcastComponentHelper;
import org.apache.camel.component.hazelcast.HazelcastConstants;
import org.apache.camel.component.hazelcast.HazelcastDefaultEndpoint;
import org.apache.camel.component.hazelcast.HazelcastDefaultProducer;
import org.apache.camel.component.hazelcast.HazelcastOperation;

public class HazelcastReplicatedmapProducer extends HazelcastDefaultProducer {

    private final ReplicatedMap<Object, Object> cache;

    public HazelcastReplicatedmapProducer(HazelcastInstance hazelcastInstance, HazelcastDefaultEndpoint endpoint,
                                          String cacheName) {
        super(endpoint);
        this.cache = hazelcastInstance.getReplicatedMap(cacheName);
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        Map<String, Object> headers = exchange.getIn().getHeaders();

        // GET header parameters
        Object oid = null;

        if (headers.containsKey(HazelcastConstants.OBJECT_ID)) {
            oid = headers.get(HazelcastConstants.OBJECT_ID);
        }

        final HazelcastOperation operation = lookupOperation(exchange);

        switch (operation) {
            case PUT:
                this.put(oid, exchange);
                break;

            case GET:
                this.get(oid, exchange);
                break;

            case DELETE:
                this.delete(oid);
                break;

            case CLEAR:
                this.clear();
                break;

            case CONTAINS_KEY:
                this.containsKey(oid, exchange);
                break;

            case CONTAINS_VALUE:
                this.containsValue(exchange);
                break;

            default:
                throw new IllegalArgumentException(
                        String.format("The value '%s' is not allowed for parameter '%s' on the MULTIMAP cache.", operation,
                                HazelcastConstants.OPERATION));
        }

        // finally copy headers
        HazelcastComponentHelper.copyHeaders(exchange);
    }

    private void put(Object oid, Exchange exchange) {
        Object body = exchange.getIn().getBody();
        this.cache.put(oid, body);
    }

    private void get(Object oid, Exchange exchange) {
        exchange.getMessage().setBody(this.cache.get(oid));
    }

    private void delete(Object oid) {
        this.cache.remove(oid);
    }

    private void clear() {
        this.cache.clear();
    }

    private void containsKey(Object oid, Exchange exchange) {
        exchange.getMessage().setBody(this.cache.containsKey(oid));
    }

    private void containsValue(Exchange exchange) {
        Object body = exchange.getIn().getBody();
        exchange.getMessage().setBody(this.cache.containsValue(body));
    }
}
