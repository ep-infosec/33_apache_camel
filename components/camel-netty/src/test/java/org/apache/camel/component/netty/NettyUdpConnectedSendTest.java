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
package org.apache.camel.component.netty;

import java.net.InetSocketAddress;
import java.util.List;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertTrue;

//We need to run the tests with fix order
@TestMethodOrder(MethodOrderer.MethodName.class)
public class NettyUdpConnectedSendTest extends BaseNettyTest {
    private static final String SEND_STRING = "***<We all love camel>***";
    private static final int SEND_COUNT = 10;
    private volatile int receivedCount;
    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private Channel serverChannel;

    public void createNettyUdpReceiver() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        channel.pipeline().addLast(new UdpHandler());
                        channel.pipeline().addLast(new ContentHandler());
                    }
                }).localAddress(new InetSocketAddress(getPort()));
    }

    public void bind() {
        serverChannel = bootstrap.bind().syncUninterruptibly().channel();
    }

    public void stop() {
        // Just make sure we shutdown the listener after the call
        serverChannel.close().syncUninterruptibly();
        group.shutdownGracefully().syncUninterruptibly();
    }

    @Test
    public void sendConnectedUdpWithServer() {
        createNettyUdpReceiver();
        bind();
        for (int i = 0; i < SEND_COUNT; ++i) {
            template.sendBody("direct:in", SEND_STRING);
        }
        stop();
        assertTrue(receivedCount > 0, "We should have received some datagrams");
    }

    @Test
    @Disabled("This test would be failed in JDK7 sometimes")
    public void sendBodyWithoutReceiver() {
        int exceptionCount = 0;
        for (int i = 0; i < SEND_COUNT; ++i) {
            try {
                template.sendBody("direct:in", SEND_STRING);
            } catch (Exception ex) {
                ++exceptionCount;
            }
        }
        assertTrue(exceptionCount > 0, "There should at least one exception because port is unreachable");
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:in").to("netty:udp://localhost:{{port}}?sync=false&textline=true");
            }
        };
    }

    public class UdpHandler extends MessageToMessageDecoder<DatagramPacket> {
        @Override
        protected void decode(
                ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> objects) {
            objects.add(datagramPacket.content().toString(CharsetUtil.UTF_8));
        }
    }

    public class ContentHandler extends SimpleChannelInboundHandler<String> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) {
            receivedCount++;
        }
    }
}
