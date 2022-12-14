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
package org.apache.camel.component.mail.security;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLHandshakeException;

import org.apache.camel.BindToRegistry;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mail.MailConstants;
import org.apache.camel.component.mail.MailTestHelper;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test of integration between the mail component and JSSE Configuration Utility. This test does not easily automate.
 * This test is therefore ignored and the source is maintained here for easier development in the future.
 */
@Disabled
public class SslContextParametersMailRouteTest extends CamelTestSupport {

    private String email = "USERNAME@gmail.com";
    private String username = "USERNAME@gmail.com";
    private String imapHost = "imap.gmail.com";
    private String smtpHost = "smtp.gmail.com";
    private String password = "PASSWORD";

    @BindToRegistry("sslContextParameters")
    private SSLContextParameters params = MailTestHelper.createSslContextParameters();

    @Test
    public void testSendAndReceiveMails() throws Exception {

        context.addRoutes(new RouteBuilder() {
            public void configure() {

                from("imaps://" + imapHost + "?username=" + username + "&password=" + password
                     + "&delete=false&unseen=true&fetchSize=1&useFixedDelay=true&initialDelay=100&delay=100").to("mock:in");

                from("direct:in").to("smtps://" + smtpHost + "?username=" + username + "&password=" + password);
            }
        });

        context.start();

        MockEndpoint resultEndpoint = getMockEndpoint("mock:in");
        resultEndpoint.expectedBodiesReceived("Test Email Body\r\n");

        Map<String, Object> headers = new HashMap<>();
        headers.put("To", email);
        headers.put("From", email);
        headers.put(MailConstants.MAIL_REPLY_TO, email);
        headers.put("Subject", "SSL/TLS Test");

        template.sendBodyAndHeaders("direct:in", "Test Email Body", headers);

        resultEndpoint.assertIsSatisfied();
    }

    @Test
    public void testSendAndReceiveMailsWithCustomTrustStore() throws Exception {

        context.addRoutes(new RouteBuilder() {
            public void configure() {

                from("direct:in").to("smtps://" + smtpHost + "?username=" + username + "&password=" + password
                                     + "&sslContextParameters=#sslContextParameters");
            }
        });

        context.start();

        Map<String, Object> headers = new HashMap<>();
        headers.put("To", email);
        headers.put("From", email);
        headers.put(MailConstants.MAIL_REPLY_TO, email);
        headers.put("Subject", "SSL/TLS Test");

        try {
            template.sendBodyAndHeaders("direct:in", "Test Email Body", headers);
            fail("Should have thrown exception");
        } catch (CamelExecutionException e) {
            assertTrue(e.getCause().getCause() instanceof SSLHandshakeException);
            assertTrue(e.getCause().getCause().getMessage()
                    .contains("unable to find valid certification path to requested target"));
        }
    }

    /**
     * Stop Camel startup.
     */
    @Override
    public boolean isUseAdviceWith() {
        return true;
    }
}
