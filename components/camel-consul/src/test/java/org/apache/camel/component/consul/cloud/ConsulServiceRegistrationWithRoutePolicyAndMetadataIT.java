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

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cloud.ServiceDefinition;
import org.apache.camel.impl.cloud.ServiceRegistrationRoutePolicy;

public class ConsulServiceRegistrationWithRoutePolicyAndMetadataIT extends ConsulServiceRegistrationTestBase {
    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                fromF("jetty:http://0.0.0.0:%d/service/endpoint", SERVICE_PORT).routeId(SERVICE_ID)
                        .routeProperty(ServiceDefinition.SERVICE_META_ID, SERVICE_ID)
                        .routeProperty(ServiceDefinition.SERVICE_META_NAME, SERVICE_NAME)
                        .routePolicy(new ServiceRegistrationRoutePolicy()).noAutoStartup()
                        .to("log:service-registry?level=INFO");
            }
        };
    }
}
