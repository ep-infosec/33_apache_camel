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
package org.apache.camel.component.jmx;

import java.util.Hashtable;

import javax.management.ObjectName;

import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for the endpoint. Most of the params in the endpoint are set via the endpoint helper so there's no much beyond
 * some sanity checks here.
 */
public class JMXEndpointTest {

    DefaultCamelContext context;

    @BeforeEach
    public void setUp() {
        context = new DefaultCamelContext();
    }

    @Test
    public void setObjectNameThrowsWhenObjectPropertiesIsSet() {
        JMXEndpoint ep = new JMXEndpoint("urn:ignored", new JMXComponent());
        ep.setObjectProperties(new Hashtable<String, String>());
        try {
            // should fault since objectName is mutex with objectProperties
            ep.setObjectName("foo");
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void defaultsToXml() {
        JMXEndpoint ep = context.getEndpoint("jmx:platform?objectDomain=FooDomain&objectName=theObjectName", JMXEndpoint.class);
        assertTrue(ep.isXML());
    }

    @Test
    public void formatRaw() {
        JMXEndpoint ep = context.getEndpoint("jmx:platform?objectDomain=FooDomain&objectName=theObjectName&format=raw",
                JMXEndpoint.class);
        assertFalse(ep.isXML());
        assertEquals("raw", ep.getFormat());
    }

    @Test
    public void getJMXObjectName() throws Exception {
        JMXEndpoint ep = context.getEndpoint("jmx:platform?objectDomain=FooDomain&objectName=theObjectName", JMXEndpoint.class);
        ObjectName on = ep.getJMXObjectName();
        assertNotNull(on);
        assertEquals("FooDomain:name=theObjectName", on.toString());
    }

    @Test
    public void getJMXObjectNameWithProps() throws Exception {
        JMXEndpoint ep = context.getEndpoint("jmx:platform?objectDomain=FooDomain&key.name=theObjectName", JMXEndpoint.class);
        ObjectName on = ep.getJMXObjectName();
        assertNotNull(on);
        assertEquals("FooDomain:name=theObjectName", on.toString());
    }

    @Test
    public void getJMXObjectNameCached() throws Exception {
        JMXEndpoint ep = context.getEndpoint("jmx:platform?objectDomain=FooDomain&key.name=theObjectName", JMXEndpoint.class);
        ObjectName on = ep.getJMXObjectName();
        assertNotNull(on);
        assertSame(on, ep.getJMXObjectName());
    }

    @Test
    public void platformServer() {
        JMXEndpoint ep = context.getEndpoint("jmx:platform?objectDomain=FooDomain&key.name=theObjectName", JMXEndpoint.class);
        assertTrue(ep.isPlatformServer());
        assertEquals("platform", ep.getServerURL());
    }

    @Test
    public void remoteServer() {
        JMXEndpoint ep = context.getEndpoint(
                "jmx:service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi?objectDomain=FooDomain&key.name=theObjectName",
                JMXEndpoint.class);
        assertFalse(ep.isPlatformServer());
        assertEquals("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi", ep.getServerURL());

        ep = context.getEndpoint(
                "jmx://service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi?objectDomain=FooDomain&key.name=theObjectName",
                JMXEndpoint.class);
        assertFalse(ep.isPlatformServer());
        assertEquals("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi", ep.getServerURL());
    }

    @Test
    public void noProducer() throws Exception {
        JMXEndpoint ep = context.getEndpoint("jmx:platform?objectDomain=FooDomain&key.name=theObjectName", JMXEndpoint.class);
        try {
            ep.createProducer();
            fail("producer pattern is not supported");
        } catch (UnsupportedOperationException e) {
        }
    }

    @Test
    public void credentials() {
        JMXEndpoint ep = context.getEndpoint(
                "jmx:platform?objectDomain=FooDomain&key.name=theObjectName&user=user1&password=1234", JMXEndpoint.class);
        assertEquals("user1", ep.getUser());
        assertEquals("1234", ep.getPassword());
    }

    @Test
    public void noObservedAttribute() throws Exception {
        JMXEndpoint ep = context.getEndpoint("jmx:platform?objectDomain=FooDomain&objectName=theObjectName&monitorType=string",
                JMXEndpoint.class);
        try {
            ep.createConsumer(null);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            assertEquals(JMXEndpoint.ERR_OBSERVED_ATTRIBUTE, e.getMessage());
        }
    }

    @Test
    public void noStringToCompare() throws Exception {
        JMXEndpoint ep = context.getEndpoint(
                "jmx:platform?objectDomain=FooDomain&objectName=theObjectName&monitorType=string&observedAttribute=foo",
                JMXEndpoint.class);
        try {
            ep.createConsumer(null);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            assertEquals(JMXEndpoint.ERR_STRING_TO_COMPARE, e.getMessage());
        }
    }

    @Test
    public void noNotifyDifferOrNotifyMatch() throws Exception {
        JMXEndpoint ep = context.getEndpoint(
                "jmx:platform?objectDomain=FooDomain&objectName=theObjectName&monitorType=string&observedAttribute=foo&stringToCompare=foo",
                JMXEndpoint.class);
        try {
            ep.createConsumer(null);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            assertEquals(JMXEndpoint.ERR_STRING_NOTIFY, e.getMessage());
        }
    }

    @Test
    public void noNotifyHighOrNotifyLow() throws Exception {
        JMXEndpoint ep = context.getEndpoint(
                "jmx:platform?objectDomain=FooDomain&objectName=theObjectName&monitorType=gauge&observedAttribute=foo",
                JMXEndpoint.class);
        try {
            ep.createConsumer(null);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            assertEquals(JMXEndpoint.ERR_GAUGE_NOTIFY, e.getMessage());
        }
    }

    @Test
    public void noThresholdHigh() throws Exception {
        JMXEndpoint ep = context.getEndpoint(
                "jmx:platform?objectDomain=FooDomain&objectName=theObjectName&monitorType=gauge&observedAttribute=foo&thresholdLow=100&notifyHigh=true",
                JMXEndpoint.class);
        try {
            ep.createConsumer(null);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            assertEquals(JMXEndpoint.ERR_THRESHOLD_HIGH, e.getMessage());
        }
    }

    @Test
    public void noThresholdLow() throws Exception {
        JMXEndpoint ep = context.getEndpoint(
                "jmx:platform?objectDomain=FooDomain&objectName=theObjectName&monitorType=gauge&observedAttribute=foo&thresholdHigh=100&notifyHigh=true",
                JMXEndpoint.class);
        try {
            ep.createConsumer(null);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            assertEquals(JMXEndpoint.ERR_THRESHOLD_LOW, e.getMessage());
        }
    }

    @Test
    public void remoteServerWithMonitor() throws Exception {
        JMXEndpoint ep = context.getEndpoint(
                "jmx:service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi?objectDomain=FooDomain&key.name=theObjectName&monitorType=gauge",
                JMXEndpoint.class);
        try {
            ep.createConsumer(null);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            assertEquals(JMXEndpoint.ERR_PLATFORM_SERVER, e.getMessage());
        }
    }
}
