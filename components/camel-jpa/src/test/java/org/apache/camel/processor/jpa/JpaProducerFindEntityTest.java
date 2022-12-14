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
package org.apache.camel.processor.jpa;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.examples.SendEmail;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JpaProducerFindEntityTest extends AbstractJpaTest {
    protected static final String SELECT_ALL_STRING = "select x from " + SendEmail.class.getName() + " x";

    @Test
    public void testFind() throws Exception {
        SendEmail one = new SendEmail("foo");
        SendEmail two = new SendEmail("bar");

        save(one);
        save(two);

        long id = one.getId();
        long id2 = two.getId();

        getMockEndpoint("mock:result").expectedMessageCount(2);

        SendEmail out = template.requestBody("direct:start", id, SendEmail.class);
        SendEmail out2 = template.requestBody("direct:start", id2, SendEmail.class);

        MockEndpoint.assertIsSatisfied(context);

        assertEquals(one.getAddress(), out.getAddress());
        assertEquals(two.getAddress(), out2.getAddress());
    }

    protected void save(final Object persistable) {
        transactionTemplate.execute(new TransactionCallback<Object>() {
            public Object doInTransaction(TransactionStatus status) {
                entityManager.joinTransaction();
                entityManager.persist(persistable);
                entityManager.flush();
                return null;
            }
        });
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start").to("jpa://" + SendEmail.class.getName() + "?findEntity=true").to("mock:result");
            }
        };
    }

    @Override
    protected String routeXml() {
        return "org/apache/camel/processor/jpa/springJpaRouteTest.xml";
    }

    @Override
    protected String selectAllString() {
        return SELECT_ALL_STRING;
    }

}
