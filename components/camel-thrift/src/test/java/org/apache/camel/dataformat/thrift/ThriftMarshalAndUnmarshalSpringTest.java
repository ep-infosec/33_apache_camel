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
package org.apache.camel.dataformat.thrift;

import org.apache.camel.CamelException;
import org.apache.camel.FailedToCreateRouteException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.dataformat.thrift.generated.Operation;
import org.apache.camel.dataformat.thrift.generated.Work;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ThriftMarshalAndUnmarshalSpringTest extends CamelSpringTestSupport {
    private static final String WORK_TEST_COMMENT = "This is a test thrift data";
    private static final int WORK_TEST_NUM1 = 1;
    private static final int WORK_TEST_NUM2 = 100;
    private static final Operation WORK_TEST_OPERATION = Operation.MULTIPLY;

    @Override
    protected ClassPathXmlApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("org/apache/camel/dataformat/thrift/springDataFormat.xml");
    }

    @Test
    public void testMarshalAndUnmarshalWithDataFormat() throws Exception {
        marshalAndUnmarshal("direct:in", "direct:back");
    }

    @Test
    public void testMarshalAndUnmarshalWithDSL1() throws Exception {
        marshalAndUnmarshal("direct:marshal", "direct:unmarshalA");
    }

    @Test
    public void testMarshalAndUnmarshalWithDSL2() throws Exception {
        marshalAndUnmarshal("direct:marshal", "direct:unmarshalB");
    }

    @Test
    public void testMarshalAndUnmarshalWithDSL3() {
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    from("direct:unmarshalC").unmarshal().thrift(new CamelException("wrong instance")).to("mock:reverse");
                }
            });
            fail("Expect the exception here");
        } catch (Exception ex) {
            assertTrue(ex instanceof FailedToCreateRouteException, "Expect FailedToCreateRouteException");
        }
    }

    private void marshalAndUnmarshal(String inURI, String outURI) throws Exception {
        Work input = new Work();

        input.num1 = WORK_TEST_NUM1;
        input.num2 = WORK_TEST_NUM2;
        input.op = WORK_TEST_OPERATION;
        input.comment = WORK_TEST_COMMENT;

        MockEndpoint mock = getMockEndpoint("mock:reverse");
        mock.expectedMessageCount(1);
        mock.message(0).body().isInstanceOf(Work.class);
        mock.message(0).body().isEqualTo(input);

        Object marshalled = template.requestBody(inURI, input);

        template.sendBody(outURI, marshalled);

        mock.assertIsSatisfied();

        Work output = mock.getReceivedExchanges().get(0).getIn().getBody(Work.class);
        assertEquals(WORK_TEST_COMMENT, output.getComment());
        assertEquals(WORK_TEST_OPERATION, output.getOp());
        assertEquals(WORK_TEST_NUM2, output.getNum2());
    }
}
