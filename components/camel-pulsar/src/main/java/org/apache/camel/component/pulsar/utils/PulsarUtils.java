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
package org.apache.camel.component.pulsar.utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

import org.apache.camel.spi.ExecutorServiceManager;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PulsarUtils {

    private static final Logger LOG = LoggerFactory.getLogger(PulsarUtils.class);

    private PulsarUtils() {
    }

    public static Queue<ExecutorService> stopExecutors(
            final ExecutorServiceManager executorServiceManager, final Queue<ExecutorService> executors) {
        for (ExecutorService executor : executors) {
            executorServiceManager.shutdownGraceful(executor, 500);
        }
        return new ConcurrentLinkedQueue<>();
    }

    public static Queue<Consumer<byte[]>> stopConsumers(final Queue<Consumer<byte[]>> consumers) throws PulsarClientException {
        while (!consumers.isEmpty()) {
            Consumer<byte[]> consumer = consumers.poll();
            if (consumer != null) {
                try {
                    consumer.close();
                } catch (PulsarClientException.AlreadyClosedException e) {
                    // ignore during stopping
                } catch (Exception e) {
                    LOG.debug("Error stopping consumer: {} due to {}. This exception is ignored", consumer,
                            e.getMessage(), e);
                }
            }
        }

        return new ConcurrentLinkedQueue<>();
    }

    /**
     * Pauses the Pulsar consumers.
     *
     * Once paused, a Pulsar consumer does not request any more messages from the broker. However, it will still receive
     * as many messages as it had already requested, which is equal to at most `consumerQueueSize`.
     */
    public static void pauseConsumers(final Queue<Consumer<byte[]>> consumers) {
        consumers.forEach(Consumer::pause);
    }

    public static void resumeConsumers(final Queue<Consumer<byte[]>> consumers) {
        consumers.forEach(Consumer::resume);
    }
}
