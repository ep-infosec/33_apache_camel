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
package org.apache.camel.component.aws2.sqs;

import org.apache.camel.Endpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.Protocol;
import software.amazon.awssdk.regions.Region;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SqsComponentConfigurationTest extends CamelTestSupport {

    @Test
    public void createEndpointWithMinimalConfiguration() throws Exception {
        AmazonSQSClientMock mock = new AmazonSQSClientMock();

        context.getRegistry().bind("amazonSQSClient", mock);
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        Sqs2Endpoint endpoint = (Sqs2Endpoint) component
                .createEndpoint("aws2-sqs://MyQueue?amazonSQSClient=#amazonSQSClient&accessKey=xxx&secretKey=yyy");

        assertEquals("MyQueue", endpoint.getConfiguration().getQueueName());
        assertEquals("xxx", endpoint.getConfiguration().getAccessKey());
        assertEquals("yyy", endpoint.getConfiguration().getSecretKey());
        assertNotNull(endpoint.getConfiguration().getAmazonSQSClient());
        assertNull(endpoint.getConfiguration().getAttributeNames());
        assertNull(endpoint.getConfiguration().getMessageAttributeNames());
        assertNull(endpoint.getConfiguration().getDefaultVisibilityTimeout());
        assertNull(endpoint.getConfiguration().getVisibilityTimeout());
        assertNull(endpoint.getConfiguration().getMaximumMessageSize());
        assertNull(endpoint.getConfiguration().getMessageRetentionPeriod());
        assertNull(endpoint.getConfiguration().getPolicy());
        assertNull(endpoint.getConfiguration().getRedrivePolicy());
        assertNull(endpoint.getConfiguration().getRegion());
    }

    @Test
    public void createEndpointWithOnlyAccessKeyAndSecretKey() throws Exception {
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        Sqs2Endpoint endpoint = (Sqs2Endpoint) component.createEndpoint("aws2-sqs://MyQueue?accessKey=xxx&secretKey=yyy");

        assertEquals("MyQueue", endpoint.getConfiguration().getQueueName());
        assertEquals("xxx", endpoint.getConfiguration().getAccessKey());
        assertEquals("yyy", endpoint.getConfiguration().getSecretKey());
        assertNull(endpoint.getConfiguration().getAmazonSQSClient());
        assertNull(endpoint.getConfiguration().getAttributeNames());
        assertNull(endpoint.getConfiguration().getMessageAttributeNames());
        assertNull(endpoint.getConfiguration().getDefaultVisibilityTimeout());
        assertNull(endpoint.getConfiguration().getVisibilityTimeout());
        assertNull(endpoint.getConfiguration().getMaximumMessageSize());
        assertNull(endpoint.getConfiguration().getMessageRetentionPeriod());
        assertNull(endpoint.getConfiguration().getPolicy());
        assertNull(endpoint.getConfiguration().getRedrivePolicy());
        assertNull(endpoint.getConfiguration().getRegion());
    }

    @Test
    public void createEndpointWithOnlyAccessKeyAndSecretKeyAndRegion() throws Exception {
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        Sqs2Endpoint endpoint
                = (Sqs2Endpoint) component.createEndpoint("aws2-sqs://MyQueue?accessKey=xxx&secretKey=yyy&region=US_WEST_1");

        assertEquals("MyQueue", endpoint.getConfiguration().getQueueName());
        assertEquals("xxx", endpoint.getConfiguration().getAccessKey());
        assertEquals("yyy", endpoint.getConfiguration().getSecretKey());
        assertEquals("US_WEST_1", endpoint.getConfiguration().getRegion());
        assertNull(endpoint.getConfiguration().getAmazonSQSClient());
        assertNull(endpoint.getConfiguration().getAttributeNames());
        assertNull(endpoint.getConfiguration().getMessageAttributeNames());
        assertNull(endpoint.getConfiguration().getDefaultVisibilityTimeout());
        assertNull(endpoint.getConfiguration().getVisibilityTimeout());
        assertNull(endpoint.getConfiguration().getMaximumMessageSize());
        assertNull(endpoint.getConfiguration().getMessageRetentionPeriod());
        assertNull(endpoint.getConfiguration().getPolicy());
        assertNull(endpoint.getConfiguration().getRedrivePolicy());
    }

    @Test
    public void createEndpointWithMinimalArnConfiguration() throws Exception {
        AmazonSQSClientMock mock = new AmazonSQSClientMock();

        context.getRegistry().bind("amazonSQSClient", mock);
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        Sqs2Endpoint endpoint = (Sqs2Endpoint) component
                .createEndpoint(
                        "aws2-sqs://arn:aws:sqs:us-east-1:account:MyQueue?amazonSQSClient=#amazonSQSClient&accessKey=xxx&secretKey=yyy&region=US_EAST_1");

        assertEquals("US_EAST_1", endpoint.getConfiguration().getRegion());
        assertEquals("account", endpoint.getConfiguration().getQueueOwnerAWSAccountId());
        assertEquals("MyQueue", endpoint.getConfiguration().getQueueName());
        assertEquals("xxx", endpoint.getConfiguration().getAccessKey());
    }

    @Test
    public void createEndpointAttributeNames() throws Exception {
        AmazonSQSClientMock mock = new AmazonSQSClientMock();

        context.getRegistry().bind("amazonSQSClient", mock);
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        Sqs2Endpoint endpoint = (Sqs2Endpoint) component.createEndpoint(
                "aws2-sqs://MyQueue?amazonSQSClient=#amazonSQSClient&accessKey=xxx&secretKey=yyy&attributeNames=foo,bar");

        assertEquals("MyQueue", endpoint.getConfiguration().getQueueName());
        assertEquals("xxx", endpoint.getConfiguration().getAccessKey());
        assertEquals("yyy", endpoint.getConfiguration().getSecretKey());
        assertNotNull(endpoint.getConfiguration().getAmazonSQSClient());
        assertEquals("foo,bar", endpoint.getConfiguration().getAttributeNames());
    }

    @Test
    public void createEndpointWithMinimalConfigurationAndProvidedClient() throws Exception {
        AmazonSQSClientMock mock = new AmazonSQSClientMock();

        context.getRegistry().bind("amazonSQSClient", mock);

        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        Sqs2Endpoint endpoint = (Sqs2Endpoint) component.createEndpoint("aws2-sqs://MyQueue?amazonSQSClient=#amazonSQSClient");

        assertEquals("MyQueue", endpoint.getConfiguration().getQueueName());
        assertNull(endpoint.getConfiguration().getAccessKey());
        assertNull(endpoint.getConfiguration().getSecretKey());
        assertSame(mock, endpoint.getConfiguration().getAmazonSQSClient());
        assertNull(endpoint.getConfiguration().getAttributeNames());
        assertNull(endpoint.getConfiguration().getMessageAttributeNames());
        assertNull(endpoint.getConfiguration().getDefaultVisibilityTimeout());
        assertNull(endpoint.getConfiguration().getVisibilityTimeout());
        assertNull(endpoint.getConfiguration().getMaximumMessageSize());
        assertNull(endpoint.getConfiguration().getMessageRetentionPeriod());
        assertNull(endpoint.getConfiguration().getPolicy());
        assertNull(endpoint.getConfiguration().getRedrivePolicy());
        assertNull(endpoint.getConfiguration().getRegion());
    }

    @Test
    public void createEndpointWithMaximalConfiguration() throws Exception {
        AmazonSQSClientMock mock = new AmazonSQSClientMock();

        context.getRegistry().bind("amazonSQSClient", mock);
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);

        Sqs2Endpoint endpoint = (Sqs2Endpoint) component
                .createEndpoint("aws2-sqs://MyQueue?amazonSQSClient=#amazonSQSClient&accessKey=xxx"
                                + "&secretKey=yyy&attributeNames=color,size"
                                + "&messageAttributeNames=msgColor,msgSize&defaultVisibilityTimeout=1000&visibilityTimeout=2000&maximumMessageSize=65536&messageRetentionPeriod=1209600&policy="
                                + "%7B%22Version%22%3A%222008-10-17%22%2C%22Id%22%3A%22%2F195004372649%2FMyQueue%2FSQSDefaultPolicy%22%2C%22Statement%22%3A%5B%7B%22Sid%22%3A%22Queue1ReceiveMessage%22%2C%22"
                                + "Effect%22%3A%22Allow%22%2C%22Principal%22%3A%7B%22AWS%22%3A%22*%22%7D%2C%22Action%22%3A%22SQS%3AReceiveMessage%22%2C%22Resource%22%3A%22%2F195004372649%2FMyQueue%22%7D%5D%7D"
                                + "&delaySeconds=123&receiveMessageWaitTimeSeconds=10&waitTimeSeconds=20"
                                + "&queueOwnerAWSAccountId=111222333&region=us-east-1"
                                + "&redrivePolicy={\"maxReceiveCount\":\"5\", \"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:195004372649:MyDeadLetterQueue\"}");

        assertEquals("MyQueue", endpoint.getConfiguration().getQueueName());
        assertEquals("xxx", endpoint.getConfiguration().getAccessKey());
        assertEquals("yyy", endpoint.getConfiguration().getSecretKey());
        assertNotNull(endpoint.getConfiguration().getAmazonSQSClient());
        assertEquals("color,size", endpoint.getConfiguration().getAttributeNames());
        assertEquals("msgColor,msgSize", endpoint.getConfiguration().getMessageAttributeNames());
        assertEquals(Integer.valueOf(1000), endpoint.getConfiguration().getDefaultVisibilityTimeout());
        assertEquals(Integer.valueOf(2000), endpoint.getConfiguration().getVisibilityTimeout());
        assertEquals(Integer.valueOf(65536), endpoint.getConfiguration().getMaximumMessageSize());
        assertEquals(Integer.valueOf(1209600), endpoint.getConfiguration().getMessageRetentionPeriod());
        assertEquals(
                "{\"Version\":\"2008-10-17\",\"Id\":\"/195004372649/MyQueue/SQSDefaultPolicy\",\"Statement\":[{\"Sid\":\"Queue1ReceiveMessage\",\"Effect\":\"Allow\",\"Principal\":"
                     + "{\"AWS\":\"*\"},\"Action\":\"SQS:ReceiveMessage\",\"Resource\":\"/195004372649/MyQueue\"}]}",
                endpoint.getConfiguration().getPolicy());
        assertEquals(
                "{\"maxReceiveCount\":\"5\", \"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:195004372649:MyDeadLetterQueue\"}",
                endpoint.getConfiguration().getRedrivePolicy());
        assertEquals(Integer.valueOf(123), endpoint.getConfiguration().getDelaySeconds());
        assertEquals(Integer.valueOf(10), endpoint.getConfiguration().getReceiveMessageWaitTimeSeconds());
        assertEquals(Integer.valueOf(20), endpoint.getConfiguration().getWaitTimeSeconds());
        assertEquals("111222333", endpoint.getConfiguration().getQueueOwnerAWSAccountId());
        assertEquals("us-east-1", endpoint.getConfiguration().getRegion());
    }

    @Test
    public void createEndpointWithPollConsumerConfiguration() throws Exception {
        AmazonSQSClientMock mock = new AmazonSQSClientMock();

        context.getRegistry().bind("amazonSQSClient", mock);
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        Sqs2Endpoint endpoint = (Sqs2Endpoint) component
                .createEndpoint("aws2-sqs://MyQueue?amazonSQSClient=#amazonSQSClient"
                                + "&accessKey=xxx&secretKey=yyy&initialDelay=300&delay=400&maxMessagesPerPoll=50");
        Sqs2Consumer consumer = (Sqs2Consumer) endpoint.createConsumer(null);

        assertEquals(300, consumer.getInitialDelay());
        assertEquals(400, consumer.getDelay());
        assertEquals(50, consumer.getMaxMessagesPerPoll());
    }

    @Test
    public void createEndpointWithoutAccessKeyConfiguration() {
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        assertThrows(IllegalArgumentException.class, () -> {
            component.createEndpoint("aws2-sqs://MyQueue?secretKey=yyy");
        });
    }

    @Test
    public void createEndpointWithoutSecretKeyConfiguration() {
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        assertThrows(IllegalArgumentException.class, () -> {
            component.createEndpoint("aws2-sqs://MyQueue?accessKey=xxx");
        });
    }

    // Setting extendMessageVisibility on an SQS consumer should make
    // visibilityTimeout compulsory
    @Test
    public void createEndpointWithExtendMessageVisibilityAndNoVisibilityTimeoutThrowsException() {
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        assertThrows(IllegalArgumentException.class, () -> {
            component.createEndpoint("aws2-sqs://MyQueue?accessKey=xxx&secretKey=yyy&extendMessageVisibility=true");
        });
    }

    @Test
    public void createEndpointWithExtendMessageVisibilityTrueAndVisibilityTimeoutSet() throws Exception {
        AmazonSQSClientMock mock = new AmazonSQSClientMock();

        context.getRegistry().bind("amazonSQSClient", mock);
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        assertNotNull(component
                .createEndpoint(
                        "aws2-sqs://MyQueue?amazonSQSClient=#amazonSQSClient&accessKey=xxx&secretKey=yyy&visibilityTimeout=30&extendMessageVisibility=true"));
    }

    @Test
    public void createEndpointWithExtendMessageVisibilityFalseAndVisibilityTimeoutSet() throws Exception {
        AmazonSQSClientMock mock = new AmazonSQSClientMock();

        context.getRegistry().bind("amazonSQSClient", mock);
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        assertNotNull(component
                .createEndpoint(
                        "aws2-sqs://MyQueue?amazonSQSClient=#amazonSQSClient&accessKey=xxx&secretKey=yyy&visibilityTimeout=30&extendMessageVisibility=false"));
    }

    @Test
    public void createEndpointWithoutSecretKeyAndAccessKeyConfiguration() throws Exception {
        AmazonSQSClientMock mock = new AmazonSQSClientMock();

        context.getRegistry().bind("amazonSQSClient", mock);

        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        Endpoint ep = component.createEndpoint("aws2-sqs://MyQueue?amazonSQSClient=#amazonSQSClient");
        assertNotNull(ep, "Could not create the endpoint");
    }

    @Test
    public void createEndpointWithComponentElements() throws Exception {
        AmazonSQSClientMock mock = new AmazonSQSClientMock();

        context.getRegistry().bind("amazonSQSClient", mock);
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        component.getConfiguration().setAccessKey("XXX");
        component.getConfiguration().setSecretKey("YYY");
        Sqs2Endpoint endpoint = (Sqs2Endpoint) component.createEndpoint("aws2-sqs://MyQueue?amazonSQSClient=#amazonSQSClient");

        assertEquals("MyQueue", endpoint.getConfiguration().getQueueName());
        assertEquals("XXX", endpoint.getConfiguration().getAccessKey());
        assertEquals("YYY", endpoint.getConfiguration().getSecretKey());
    }

    @Test
    public void createEndpointWithComponentAndEndpointElements() throws Exception {
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        component.getConfiguration().setAccessKey("XXX");
        component.getConfiguration().setSecretKey("YYY");
        component.getConfiguration().setRegion(Region.US_WEST_1.toString());
        Sqs2Endpoint endpoint = (Sqs2Endpoint) component
                .createEndpoint("aws2-sqs://MyQueue?accessKey=xxxxxx&secretKey=yyyyy&region=US_EAST_1");

        assertEquals("MyQueue", endpoint.getConfiguration().getQueueName());
        assertEquals("xxxxxx", endpoint.getConfiguration().getAccessKey());
        assertEquals("yyyyy", endpoint.getConfiguration().getSecretKey());
        assertEquals("US_EAST_1", endpoint.getConfiguration().getRegion());
    }

    @Test
    public void createEndpointWithoutAutoCreation() throws Exception {
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        component.getConfiguration().setAccessKey("XXX");
        component.getConfiguration().setSecretKey("YYY");
        component.getConfiguration().setRegion(Region.US_WEST_1.toString());
        Sqs2Endpoint endpoint = (Sqs2Endpoint) component
                .createEndpoint("aws2-sqs://MyQueue?accessKey=xxxxxx&secretKey=yyyyy&region=US_EAST_1&autoCreateQueue=false");

        assertEquals("MyQueue", endpoint.getConfiguration().getQueueName());
        assertEquals("xxxxxx", endpoint.getConfiguration().getAccessKey());
        assertEquals("yyyyy", endpoint.getConfiguration().getSecretKey());
        assertEquals("US_EAST_1", endpoint.getConfiguration().getRegion());
        assertEquals(false, endpoint.getConfiguration().isAutoCreateQueue());
    }

    @Test
    public void createEndpointWithComponentEndpointElementsAndProxy() throws Exception {
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        component.getConfiguration().setAccessKey("XXX");
        component.getConfiguration().setSecretKey("YYY");
        component.getConfiguration().setRegion(Region.US_WEST_1.toString());
        Sqs2Endpoint endpoint = (Sqs2Endpoint) component
                .createEndpoint(
                        "aws2-sqs://label?accessKey=xxxxxx&secretKey=yyyyy&region=US_EAST_1&proxyHost=localhost&proxyPort=9000&proxyProtocol=HTTP");

        assertEquals("xxxxxx", endpoint.getConfiguration().getAccessKey());
        assertEquals("yyyyy", endpoint.getConfiguration().getSecretKey());
        assertEquals("US_EAST_1", endpoint.getConfiguration().getRegion());
        assertEquals(Protocol.HTTP, endpoint.getConfiguration().getProxyProtocol());
        assertEquals("localhost", endpoint.getConfiguration().getProxyHost());
        assertEquals(Integer.valueOf(9000), endpoint.getConfiguration().getProxyPort());
    }

    @Test
    public void createEndpointWithOverride() throws Exception {
        Sqs2Component component = context.getComponent("aws2-sqs", Sqs2Component.class);
        Sqs2Endpoint endpoint
                = (Sqs2Endpoint) component.createEndpoint(
                        "aws2-sqs://MyQueue?accessKey=xxx&secretKey=yyy&region=US_WEST_1&overrideEndpoint=true&uriEndpointOverride=http://localhost:9090");

        assertEquals("MyQueue", endpoint.getConfiguration().getQueueName());
        assertEquals("xxx", endpoint.getConfiguration().getAccessKey());
        assertEquals("yyy", endpoint.getConfiguration().getSecretKey());
        assertEquals("US_WEST_1", endpoint.getConfiguration().getRegion());
        assertTrue(endpoint.getConfiguration().isOverrideEndpoint());
        assertEquals("http://localhost:9090", endpoint.getConfiguration().getUriEndpointOverride());

    }
}
