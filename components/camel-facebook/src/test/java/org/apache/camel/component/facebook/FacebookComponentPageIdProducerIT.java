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
package org.apache.camel.component.facebook;

import facebook4j.Post;
import facebook4j.ResponseList;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

@EnabledIf(value = "org.apache.camel.component.facebook.CamelFacebookTestSupport#hasCredentials",
           disabledReason = "Facebook credentials were not provided")
public class FacebookComponentPageIdProducerIT extends CamelFacebookTestSupport {
    public static final String APACHE_FOUNDATION_PAGE_ID = "6538157161";

    long lastTimestamp = -1;

    @Test
    public void testProducers() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:page");
        mock.expectedMinimumMessageCount(3);
        mock.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("timer:period=20000")
                        .setHeader("CamelFacebook.reading.limit", constant("10"))
                        .process(exchange -> {
                            if (lastTimestamp > 0) {
                                exchange.getIn().setHeader("CamelFacebook.reading.since", lastTimestamp);
                            }
                        })
                        .to("facebook://getPosts?" + getOauthParams() + "&userId=" + APACHE_FOUNDATION_PAGE_ID
                            + "&reading.limit=5")
                        .process(exchange -> {
                            ResponseList<Post> body = exchange.getIn().getBody(ResponseList.class);
                            log.info("Number of posts received: {}", body.size());
                            for (Post post : body) {
                                log.debug(post.toString());
                            }

                            if (!body.isEmpty()) {
                                lastTimestamp = body.get(0).getUpdatedTime().getTime();
                            }
                        })
                        .to("mock:page");
            }
        };
    }

}
