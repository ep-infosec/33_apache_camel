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
package org.apache.camel.component.milo.server;

import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test setting security policies
 */
public class ServerSetSecurityPoliciesTest extends CamelTestSupport {

    private static final Logger LOG = LoggerFactory.getLogger(ServerSetSecurityPoliciesTest.class);

    @BeforeEach
    public void setup(TestInfo testInfo) {
        final var displayName = testInfo.getDisplayName();
        LOG.info("********************************************************************************");
        LOG.info(displayName);
        LOG.info("********************************************************************************");
    }

    @Test
    public void testSetSecurityPolicies1() {
        final MiloServerComponent component = new MiloServerComponent();
        Assertions.assertDoesNotThrow(() -> component.setSecurityPoliciesById("None"));
    }

    @Test
    public void testSetSecurityPolicies2() {
        final MiloServerComponent component = new MiloServerComponent();
        Assertions.assertDoesNotThrow(
                () -> component.setSecurityPoliciesById("http://opcfoundation.org/UA/SecurityPolicy#Basic256Sha256"));
    }

    @Test
    public void testSetSecurityPolicies3() {
        final MiloServerComponent component = new MiloServerComponent();
        Assertions.assertDoesNotThrow(
                () -> component.setSecurityPoliciesById("None,http://opcfoundation.org/UA/SecurityPolicy#Basic256Sha256"));
    }

}
