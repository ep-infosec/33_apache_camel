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
package org.apache.camel.component.mina;

import org.apache.camel.ResolveEndpointFailedException;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * For testing various minor holes that hasn't been covered by other unit tests.
 */
public class MinaComponentTest extends CamelTestSupport {

    @Test
    public void testUnknownProtocol() {
        Exception e = assertThrows(ResolveEndpointFailedException.class,
                () -> template.sendBody("mina:xxx://localhost:8080", "mina:xxx://localhost:8080"),
                "Should have thrown a ResolveEndpointFailedException");

        assertTrue(e.getCause() instanceof IllegalArgumentException, "Should be an IAE exception");
        assertEquals("Unrecognised MINA protocol: xxx for uri: mina://xxx://localhost:8080", e.getCause().getMessage());
    }

    @Test
    public void testMistypedProtocol() {
        Exception e = assertThrows(ResolveEndpointFailedException.class,
                () -> template.sendBody("mina:tcp//localhost:8080", "mina:tcp//localhost:8080"),
                "Should have thrown a ResolveEndpointFailedException");

        assertTrue(e.getCause() instanceof IllegalArgumentException, "Should be an IAE exception");
        assertEquals("Unrecognised MINA protocol: null for uri: mina://tcp//localhost:8080", e.getCause().getMessage());
    }
}
