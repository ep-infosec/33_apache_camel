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
package org.apache.camel.dataformat.any23;

import java.io.File;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.eclipse.rdf4j.model.Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Any23DataFormatMarshalTest extends CamelTestSupport {

    private final String baseURI = "http://mock.foo/bar";

    @Test
    public void test() throws Exception {
        MockEndpoint resultEndpoint = resolveMandatoryEndpoint("mock:result", MockEndpoint.class);
        String contenhtml = Any23TestSupport
                .loadFileAsString(new File("src/test/resources/org/apache/camel/dataformat/any23/microformat/vcard.html"));
        template.sendBody("direct:start", contenhtml);
        List<Exchange> list = resultEndpoint.getReceivedExchanges();
        for (Exchange exchange : list) {
            Message in = exchange.getIn();
            Model resultingRDF = in.getBody(Model.class);
            assertEquals(28, resultingRDF.size());
        }
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                Any23DataFormat df = new Any23DataFormat();
                df.setBaseUri(baseURI);
                df.setOutputFormat(Any23OutputFormat.RDF4JMODEL);

                from("direct:start").unmarshal(df).to("direct:r1");
                from("direct:r1").marshal(df).to("direct:r2");
                from("direct:r2").unmarshal(df).to("mock:result");
            }
        };
    }

}
