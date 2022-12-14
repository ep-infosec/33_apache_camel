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
package org.apache.camel.component.mina;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * To test InOut exchange for the UDP protocol.
 */
public class MinaUdpWithInOutUsingPlainSocketTest extends BaseMinaTest {

    private static final Logger LOG = LoggerFactory.getLogger(MinaUdpWithInOutUsingPlainSocketTest.class);

    @Test
    public void testSendAndReceiveOnce() throws Exception {
        String out = sendAndReceiveUdpMessages("World");
        assertEquals("Hello World", out);
    }

    private String sendAndReceiveUdpMessages(String input) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("127.0.0.1");

        byte[] data = input.getBytes();

        DatagramPacket packet = new DatagramPacket(data, data.length, address, getPort());
        LOG.debug("+++ Sending data +++");
        socket.send(packet);

        byte[] buf = new byte[128];
        DatagramPacket receive = new DatagramPacket(buf, buf.length, address, getPort());
        LOG.debug("+++ Receiveing data +++");
        socket.receive(receive);

        socket.close();

        return new String(receive.getData(), 0, receive.getLength());
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {

            public void configure() {
                fromF("mina:udp://127.0.0.1:%1$s?sync=true", getPort()).process(exchange -> {
                    String s = exchange.getIn().getBody(String.class);
                    exchange.getMessage().setBody("Hello " + s);
                });
            }
        };
    }
}
