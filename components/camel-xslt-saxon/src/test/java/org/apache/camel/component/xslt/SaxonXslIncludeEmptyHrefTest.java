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
package org.apache.camel.component.xslt;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class SaxonXslIncludeEmptyHrefTest extends CamelTestSupport {

    @Test
    public void testXsltOutput() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");

        mock.expectedBodiesReceived("<?xml version=\"1.0\" encoding=\"UTF-8\"?><MyDate>February</MyDate>");
        mock.message(0).body().isInstanceOf(String.class);

        template.sendBody("direct:start", "<root>1</root>");

        MockEndpoint.assertIsSatisfied(context);
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:start")
                        .to("xslt-saxon:org/apache/camel/component/xslt/transform_includes_data.xsl")
                        .to("mock:result");
            }
        };
    }
}
