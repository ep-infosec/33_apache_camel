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
package org.apache.camel.component.jetty.rest;

import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.apache.camel.test.junit5.TestSupport.assertIsInstanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RestJettyBasicAuthTest extends CamelSpringTestSupport {

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("org/apache/camel/component/jetty/rest/RestJettyBasicAuthTest.xml");
    }

    @Test
    public void testJestDslBasicAuth() {
        String out = template.requestBody("http://localhost:9444/ping?authMethod=Basic&authUsername=donald&authPassword=duck",
                null, String.class);
        assertEquals("\"pong\"", out);

        try {
            template.requestBody("http://localhost:9444/ping?authMethod=Basic&authUsername=mickey&authPassword=duck", null,
                    String.class);
            fail("Should not login");
        } catch (Exception e) {
            HttpOperationFailedException hofe = assertIsInstanceOf(HttpOperationFailedException.class, e.getCause());
            assertEquals(401, hofe.getStatusCode());
        }
    }

}
