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
package org.apache.camel.opentracing;

import io.opentracing.noop.NoopTracer;
import io.opentracing.noop.NoopTracerFactory;
import io.opentracing.util.GlobalTracerTestUtil;
import org.apache.camel.BindToRegistry;
import org.apache.camel.CamelContext;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpentracingSpanCollectorInRegistryTest extends CamelTestSupport {

    private OpenTracingTracer openTracing;
    @BindToRegistry("tracer")
    private NoopTracer c = NoopTracerFactory.create();

    @Override
    public void doPreSetup() {
        GlobalTracerTestUtil.resetGlobalTracer();
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();

        openTracing = new OpenTracingTracer();
        openTracing.init(context);

        return context;
    }

    @Test
    public void testZipkinConfiguration() {
        assertNotNull(openTracing.getTracer());
        assertTrue(openTracing.getTracer() instanceof NoopTracer);
    }

}
