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
package org.apache.camel.component.rabbitmq;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.impl.LongStringHelper;
import org.apache.camel.Exchange;
import org.apache.camel.component.rabbitmq.integration.RabbitMQDeadLetterRoutingKeyIT;
import org.apache.camel.spi.Registry;
import org.apache.camel.support.SimpleRegistry;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.camel.test.junit5.TestSupport.assertIsInstanceOf;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RabbitMQEndpointTest extends CamelTestSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQDeadLetterRoutingKeyIT.class);

    private Envelope envelope = Mockito.mock(Envelope.class);
    private AMQP.BasicProperties properties = Mockito.mock(AMQP.BasicProperties.class);

    @Override
    protected Registry createCamelRegistry() {
        SimpleRegistry registry = new SimpleRegistry();

        Map<String, Object> args = new HashMap<>();
        args.put("foo", "bar");
        registry.bind("args", args);

        Map<String, Object> moreArgs = new HashMap<>();
        moreArgs.put("fizz", "buzz");
        registry.bind("moreArgs", moreArgs);

        Map<String, Object> evenMoreArgs = new HashMap<>();
        evenMoreArgs.put("ping", "pong");
        registry.bind("evenMoreArgs", evenMoreArgs);

        return registry;
    }

    @Test
    public void testCreatingRabbitExchangeSetsStandardHeaders() {
        RabbitMQEndpoint endpoint = context.getEndpoint("rabbitmq:localhost/exchange", RabbitMQEndpoint.class);

        String routingKey = UUID.randomUUID().toString();
        String exchangeName = UUID.randomUUID().toString();
        long tag = UUID.randomUUID().toString().hashCode();
        Boolean redelivery = new SecureRandom().nextBoolean();

        Mockito.when(envelope.getRoutingKey()).thenReturn(routingKey);
        Mockito.when(envelope.getExchange()).thenReturn(exchangeName);
        Mockito.when(envelope.getDeliveryTag()).thenReturn(tag);
        Mockito.when(envelope.isRedeliver()).thenReturn(redelivery);
        Mockito.when(properties.getHeaders()).thenReturn(null);

        byte[] body = new byte[20];
        Exchange exchange = endpoint.createRabbitExchange(envelope, properties, body);
        assertEquals(exchangeName, exchange.getIn().getHeader(RabbitMQConstants.EXCHANGE_NAME));
        assertEquals(routingKey, exchange.getIn().getHeader(RabbitMQConstants.ROUTING_KEY));
        assertEquals(tag, exchange.getIn().getHeader(RabbitMQConstants.DELIVERY_TAG));
        assertEquals(redelivery, exchange.getIn().getHeader(RabbitMQConstants.REDELIVERY_TAG));
        assertEquals(body, exchange.getIn().getBody());
    }

    @Test
    public void testExchangeNameIsOptional() {
        RabbitMQEndpoint endpoint1 = context.getEndpoint("rabbitmq:localhost/", RabbitMQEndpoint.class);
        assertEquals("", endpoint1.getExchangeName(), "Get a wrong exchange name");

        RabbitMQEndpoint endpoint2 = context.getEndpoint("rabbitmq:localhost/exchange", RabbitMQEndpoint.class);
        assertEquals("exchange", endpoint2.getExchangeName(), "Get a wrong exchange name");
    }

    @Test
    public void testCreatingRabbitExchangeSetsCustomHeaders() {
        RabbitMQEndpoint endpoint = context.getEndpoint("rabbitmq:localhost/exchange", RabbitMQEndpoint.class);

        String routingKey = UUID.randomUUID().toString();
        String exchangeName = UUID.randomUUID().toString();
        long tag = UUID.randomUUID().toString().hashCode();

        Mockito.when(envelope.getRoutingKey()).thenReturn(routingKey);
        Mockito.when(envelope.getExchange()).thenReturn(exchangeName);
        Mockito.when(envelope.getDeliveryTag()).thenReturn(tag);

        Map<String, Object> customHeaders = new HashMap<>();
        customHeaders.put("stringHeader", "A string");
        customHeaders.put("bigDecimalHeader", new BigDecimal("12.34"));
        customHeaders.put("integerHeader", 42);
        customHeaders.put("doubleHeader", 42.24);
        customHeaders.put("booleanHeader", true);
        customHeaders.put("dateHeader", new Date(0));
        customHeaders.put("byteArrayHeader", "foo".getBytes());
        customHeaders.put("longStringHeader", LongStringHelper.asLongString("Some really long string"));
        customHeaders.put("timestampHeader", new Timestamp(4200));
        customHeaders.put("byteHeader", (byte) 0);
        customHeaders.put("floatHeader", (float) 42.4242);
        customHeaders.put("longHeader", 420000000000000000L);
        Mockito.when(properties.getHeaders()).thenReturn(customHeaders);

        byte[] body = new byte[20];
        Exchange exchange = endpoint.createRabbitExchange(envelope, properties, body);
        assertEquals(exchangeName, exchange.getIn().getHeader(RabbitMQConstants.EXCHANGE_NAME));
        assertEquals(routingKey, exchange.getIn().getHeader(RabbitMQConstants.ROUTING_KEY));
        assertEquals(tag, exchange.getIn().getHeader(RabbitMQConstants.DELIVERY_TAG));
        assertEquals("A string", exchange.getIn().getHeader("stringHeader"));
        assertEquals(new BigDecimal("12.34"), exchange.getIn().getHeader("bigDecimalHeader"));
        assertEquals(42, exchange.getIn().getHeader("integerHeader"));
        assertEquals(42.24, exchange.getIn().getHeader("doubleHeader"));
        assertEquals(true, exchange.getIn().getHeader("booleanHeader"));
        assertEquals(new Date(0), exchange.getIn().getHeader("dateHeader"));
        assertArrayEquals("foo".getBytes(), (byte[]) exchange.getIn().getHeader("byteArrayHeader"));
        assertEquals("Some really long string", exchange.getIn().getHeader("longStringHeader"));
        assertEquals(new Timestamp(4200), exchange.getIn().getHeader("timestampHeader"));
        assertEquals((byte) 0, exchange.getIn().getHeader("byteHeader"));
        assertEquals((float) 42.4242, exchange.getIn().getHeader("floatHeader"));
        assertEquals(420000000000000000L, exchange.getIn().getHeader("longHeader"));
        assertEquals(body, exchange.getIn().getBody());
    }

    @Test
    public void creatingExecutorUsesThreadPoolSettings() {
        RabbitMQEndpoint endpoint
                = context.getEndpoint("rabbitmq:localhost/exchange?threadPoolSize=20", RabbitMQEndpoint.class);
        assertEquals(20, endpoint.getThreadPoolSize());

        ThreadPoolExecutor executor = assertIsInstanceOf(ThreadPoolExecutor.class, endpoint.createExecutor());
        assertEquals(20, executor.getCorePoolSize());
    }

    @Test
    public void createEndpointWithAutoAckDisabled() {
        RabbitMQEndpoint endpoint = context.getEndpoint("rabbitmq:localhost/exchange?autoAck=false", RabbitMQEndpoint.class);
        assertFalse(endpoint.isAutoAck());
    }

    @Test
    public void assertSingleton() {
        RabbitMQEndpoint endpoint = context.getEndpoint("rabbitmq:localhost/exchange", RabbitMQEndpoint.class);
        assertTrue(endpoint.isSingleton());
    }

    @Test
    public void testMultiArgsPopulateCorrectEndpointProperties() {
        RabbitMQEndpoint endpoint = context.getEndpoint(
                "rabbitmq:localhost/exchange?arg.exchange.e1=v1&arg.exchange.e2=v2&arg.queue.q1=v3&arg.binding.b1=v4&arg.dlq.queue.dq1=v5&arg.dlq.binding.db1=v6",
                RabbitMQEndpoint.class);
        assertEquals(6, endpoint.getArgs().size(), "Wrong number of args");
        assertEquals(1, endpoint.getBindingArgs().size(), "Wrong number of args (binding)");
        assertEquals(2, endpoint.getExchangeArgs().size(), "Wrong number of args (exchange)");
        assertEquals(1, endpoint.getQueueArgs().size(), "Wrong number of args (queue)");
        assertEquals(1, endpoint.getDlqArgs().size(), "Wrong number of args (dlq.queue)");
        assertEquals(1, endpoint.getDlqBindingArgs().size(), "Wrong number of args (dlq.binding)");
    }

    @Test
    public void brokerEndpointAddressesSettings() {
        RabbitMQEndpoint endpoint = context.getEndpoint("rabbitmq:localhost/exchange?addresses=server1:12345,server2:12345",
                RabbitMQEndpoint.class);
        assertEquals(2, endpoint.parseAddresses().length, "Wrong size of endpoint addresses.");
        assertEquals(new Address("server1", 12345), endpoint.parseAddresses()[0], "Get a wrong endpoint address.");
        assertEquals(new Address("server2", 12345), endpoint.parseAddresses()[1], "Get a wrong endpoint address.");
    }

    private ConnectionFactory createConnectionFactory(String uri) throws TimeoutException {
        RabbitMQEndpoint endpoint = context.getEndpoint(uri, RabbitMQEndpoint.class);
        try {
            endpoint.connect(Executors.newSingleThreadExecutor());
        } catch (IOException ioExc) {
            // Doesn't matter if RabbitMQ is not available
            LOGGER.debug("RabbitMQ not available", ioExc);
        }
        return endpoint.getConnectionFactory();
    }

    @Test
    public void testCreateConnectionFactoryDefault() throws Exception {
        ConnectionFactory connectionFactory = createConnectionFactory("rabbitmq:localhost:1234/exchange");

        assertEquals("localhost", connectionFactory.getHost());
        assertEquals(1234, connectionFactory.getPort());
        assertEquals(ConnectionFactory.DEFAULT_VHOST, connectionFactory.getVirtualHost());
        assertEquals(ConnectionFactory.DEFAULT_USER, connectionFactory.getUsername());
        assertEquals(ConnectionFactory.DEFAULT_PASS, connectionFactory.getPassword());
        assertEquals(ConnectionFactory.DEFAULT_CONNECTION_TIMEOUT, connectionFactory.getConnectionTimeout());
        assertEquals(ConnectionFactory.DEFAULT_CHANNEL_MAX, connectionFactory.getRequestedChannelMax());
        assertEquals(ConnectionFactory.DEFAULT_FRAME_MAX, connectionFactory.getRequestedFrameMax());
        assertEquals(ConnectionFactory.DEFAULT_HEARTBEAT, connectionFactory.getRequestedHeartbeat());
        assertFalse(connectionFactory.isSSL());
        assertTrue(connectionFactory.isAutomaticRecoveryEnabled());
        assertEquals(5000, connectionFactory.getNetworkRecoveryInterval());
        assertTrue(connectionFactory.isTopologyRecoveryEnabled());
    }

    @Test
    public void testCreateConnectionFactoryCustom() throws Exception {
        ConnectionFactory connectionFactory
                = createConnectionFactory("rabbitmq:localhost:1234/exchange" + "?username=userxxx" + "&password=passxxx"
                                          + "&connectionTimeout=123"
                                          + "&requestedChannelMax=456" + "&requestedFrameMax=789" + "&requestedHeartbeat=987"
                                          + "&sslProtocol=true"
                                          + "&automaticRecoveryEnabled=true" + "&networkRecoveryInterval=654"
                                          + "&topologyRecoveryEnabled=false");

        assertEquals("localhost", connectionFactory.getHost());
        assertEquals(1234, connectionFactory.getPort());
        assertEquals("userxxx", connectionFactory.getUsername());
        assertEquals("passxxx", connectionFactory.getPassword());
        assertEquals(123, connectionFactory.getConnectionTimeout());
        assertEquals(456, connectionFactory.getRequestedChannelMax());
        assertEquals(789, connectionFactory.getRequestedFrameMax());
        assertEquals(987, connectionFactory.getRequestedHeartbeat());
        assertTrue(connectionFactory.isSSL());
        assertTrue(connectionFactory.isAutomaticRecoveryEnabled());
        assertEquals(654, connectionFactory.getNetworkRecoveryInterval());
        assertFalse(connectionFactory.isTopologyRecoveryEnabled());
    }

    @Test
    public void createEndpointWithTransferExceptionEnabled() {
        RabbitMQEndpoint endpoint
                = context.getEndpoint("rabbitmq:localhost/exchange?transferException=true", RabbitMQEndpoint.class);
        assertEquals(true, endpoint.isTransferException());
    }

    @Test
    public void createEndpointWithReplyTimeout() {
        RabbitMQEndpoint endpoint
                = context.getEndpoint("rabbitmq:localhost/exchange?requestTimeout=2000", RabbitMQEndpoint.class);
        assertEquals(2000, endpoint.getRequestTimeout());
    }

    @Test
    public void createEndpointWithRequestTimeoutCheckerInterval() {
        RabbitMQEndpoint endpoint
                = context.getEndpoint("rabbitmq:localhost/exchange?requestTimeoutCheckerInterval=1000", RabbitMQEndpoint.class);
        assertEquals(1000, endpoint.getRequestTimeoutCheckerInterval());
    }

    @Test
    public void createEndpointWithSkipQueueDeclareEnabled() {
        RabbitMQEndpoint endpoint
                = context.getEndpoint("rabbitmq:localhost/exchange?skipQueueDeclare=true", RabbitMQEndpoint.class);
        assertTrue(endpoint.isSkipQueueDeclare());
    }

    @Test
    public void createEndpointWithSkipExchangeDeclareEnabled() {
        RabbitMQEndpoint endpoint
                = context.getEndpoint("rabbitmq:localhost/exchange?skipExchangeDeclare=true", RabbitMQEndpoint.class);
        assertTrue(endpoint.isSkipExchangeDeclare());
    }

    @Test
    public void createEndpointWithSkipQueueBindEndabled() {
        RabbitMQEndpoint endpoint
                = context.getEndpoint("rabbitmq:localhost/exchange?SkipQueueBind=true", RabbitMQEndpoint.class);
        assertTrue(endpoint.isSkipQueueBind());
    }

    @Test
    public void createEndpointWithExclusiveEnabled() {
        RabbitMQEndpoint endpoint = context.getEndpoint("rabbitmq:localhost/exchange?exclusive=true", RabbitMQEndpoint.class);
        assertTrue(endpoint.isExclusive());
    }

    @Test
    public void createEndpointWithExclusiveConsumerEnabled() {
        RabbitMQEndpoint endpoint
                = context.getEndpoint("rabbitmq:localhost/exchange?exclusiveConsumer=true", RabbitMQEndpoint.class);
        assertTrue(endpoint.isExclusiveConsumer());
    }

    @Test
    public void createEndpointWithPassiveEnabled() {
        RabbitMQEndpoint endpoint = context.getEndpoint("rabbitmq:localhost/exchange?passive=true", RabbitMQEndpoint.class);
        assertTrue(endpoint.isPassive());
    }

    @Test
    public void testEndpointArgsIssue() {
        RabbitMQEndpoint endpoint1 = context.getEndpoint("rabbitmq://localhost:5672/mydirectdelayed?queue=testQ4"
                                                         + "&routingKey=testKey&username=me&password=mypwd&threadPoolSize=1&concurrentConsumers=1&autoDelete=false"
                                                         + "&vhost=myvhost&arg.queue.x-single-active-consumer=true&arg.exchange.x-delayed-type=direct&exchangeType=x-delayed-message",
                RabbitMQEndpoint.class);

        assertNotNull(endpoint1.getArgs());
        assertEquals(2, endpoint1.getArgs().size());
        assertNotNull(endpoint1.getExchangeArgs());
        assertEquals(1, endpoint1.getExchangeArgs().size());
        assertNotNull(endpoint1.getQueueArgs());
        assertEquals(1, endpoint1.getQueueArgs().size());
    }

}
