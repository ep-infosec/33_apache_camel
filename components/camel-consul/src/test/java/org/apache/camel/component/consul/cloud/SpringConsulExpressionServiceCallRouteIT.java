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
package org.apache.camel.component.consul.cloud;

import java.util.List;

import org.apache.camel.impl.cloud.DefaultServiceCallProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringConsulExpressionServiceCallRouteIT extends SpringConsulServiceCallRouteTest {
    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext(
                "org/apache/camel/component/consul/cloud/SpringConsulExpressionServiceCallRouteTest.xml");
    }

    @Test
    public void testServiceCallConfiguration() {
        List<DefaultServiceCallProcessor> processors = findServiceCallProcessors();

        Assertions.assertFalse(processors.isEmpty());
        Assertions.assertEquals(2, processors.size());
    }
}
