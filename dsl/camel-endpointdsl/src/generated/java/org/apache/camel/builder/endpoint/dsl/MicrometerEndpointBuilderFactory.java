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
package org.apache.camel.builder.endpoint.dsl;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;
import javax.annotation.Generated;
import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.AbstractEndpointBuilder;

/**
 * Collect various metrics directly from Camel routes using the Micrometer
 * library.
 * 
 * Generated by camel build tools - do NOT edit this file!
 */
@Generated("org.apache.camel.maven.packaging.EndpointDslMojo")
public interface MicrometerEndpointBuilderFactory {


    /**
     * Builder for endpoint for the Micrometer component.
     */
    public interface MicrometerEndpointBuilder
            extends
                EndpointProducerBuilder {
        default AdvancedMicrometerEndpointBuilder advanced() {
            return (AdvancedMicrometerEndpointBuilder) this;
        }
        /**
         * Action expression when using timer type.
         * 
         * The option is a: &lt;code&gt;java.lang.String&lt;/code&gt; type.
         * 
         * Group: producer
         * 
         * @param action the value to set
         * @return the dsl builder
         */
        default MicrometerEndpointBuilder action(String action) {
            doSetProperty("action", action);
            return this;
        }
        /**
         * Decrement value expression when using counter type.
         * 
         * The option is a: &lt;code&gt;java.lang.String&lt;/code&gt; type.
         * 
         * Group: producer
         * 
         * @param decrement the value to set
         * @return the dsl builder
         */
        default MicrometerEndpointBuilder decrement(String decrement) {
            doSetProperty("decrement", decrement);
            return this;
        }
        /**
         * Increment value expression when using counter type.
         * 
         * The option is a: &lt;code&gt;java.lang.String&lt;/code&gt; type.
         * 
         * Group: producer
         * 
         * @param increment the value to set
         * @return the dsl builder
         */
        default MicrometerEndpointBuilder increment(String increment) {
            doSetProperty("increment", increment);
            return this;
        }
        /**
         * Description of metrics.
         * 
         * The option is a: &lt;code&gt;java.lang.String&lt;/code&gt; type.
         * 
         * Group: producer
         * 
         * @param metricsDescription the value to set
         * @return the dsl builder
         */
        default MicrometerEndpointBuilder metricsDescription(
                String metricsDescription) {
            doSetProperty("metricsDescription", metricsDescription);
            return this;
        }
        /**
         * Value expression when using histogram type.
         * 
         * The option is a: &lt;code&gt;java.lang.String&lt;/code&gt; type.
         * 
         * Group: producer
         * 
         * @param value the value to set
         * @return the dsl builder
         */
        default MicrometerEndpointBuilder value(String value) {
            doSetProperty("value", value);
            return this;
        }
    }

    /**
     * Advanced builder for endpoint for the Micrometer component.
     */
    public interface AdvancedMicrometerEndpointBuilder
            extends
                EndpointProducerBuilder {
        default MicrometerEndpointBuilder basic() {
            return (MicrometerEndpointBuilder) this;
        }
        /**
         * Whether the producer should be started lazy (on the first message).
         * By starting lazy you can use this to allow CamelContext and routes to
         * startup in situations where a producer may otherwise fail during
         * starting and cause the route to fail being started. By deferring this
         * startup to be lazy then the startup failure can be handled during
         * routing messages via Camel's routing error handlers. Beware that when
         * the first message is processed then creating and starting the
         * producer may take a little time and prolong the total processing time
         * of the processing.
         * 
         * The option is a: &lt;code&gt;boolean&lt;/code&gt; type.
         * 
         * Default: false
         * Group: producer (advanced)
         * 
         * @param lazyStartProducer the value to set
         * @return the dsl builder
         */
        default AdvancedMicrometerEndpointBuilder lazyStartProducer(
                boolean lazyStartProducer) {
            doSetProperty("lazyStartProducer", lazyStartProducer);
            return this;
        }
        /**
         * Whether the producer should be started lazy (on the first message).
         * By starting lazy you can use this to allow CamelContext and routes to
         * startup in situations where a producer may otherwise fail during
         * starting and cause the route to fail being started. By deferring this
         * startup to be lazy then the startup failure can be handled during
         * routing messages via Camel's routing error handlers. Beware that when
         * the first message is processed then creating and starting the
         * producer may take a little time and prolong the total processing time
         * of the processing.
         * 
         * The option will be converted to a &lt;code&gt;boolean&lt;/code&gt;
         * type.
         * 
         * Default: false
         * Group: producer (advanced)
         * 
         * @param lazyStartProducer the value to set
         * @return the dsl builder
         */
        default AdvancedMicrometerEndpointBuilder lazyStartProducer(
                String lazyStartProducer) {
            doSetProperty("lazyStartProducer", lazyStartProducer);
            return this;
        }
    }

    public interface MicrometerBuilders {
        /**
         * Micrometer (camel-micrometer)
         * Collect various metrics directly from Camel routes using the
         * Micrometer library.
         * 
         * Category: monitoring
         * Since: 2.22
         * Maven coordinates: org.apache.camel:camel-micrometer
         * 
         * @return the dsl builder for the headers' name.
         */
        default MicrometerHeaderNameBuilder micrometer() {
            return MicrometerHeaderNameBuilder.INSTANCE;
        }
        /**
         * Micrometer (camel-micrometer)
         * Collect various metrics directly from Camel routes using the
         * Micrometer library.
         * 
         * Category: monitoring
         * Since: 2.22
         * Maven coordinates: org.apache.camel:camel-micrometer
         * 
         * Syntax: <code>micrometer:metricsType:metricsName</code>
         * 
         * Path parameter: metricsType (required)
         * Type of metrics
         * There are 3 enums and the value can be one of: counter,
         * distribution_summary, timer
         * 
         * Path parameter: metricsName (required)
         * Name of metrics
         * 
         * Path parameter: tags
         * Tags of metrics
         * 
         * @param path metricsType:metricsName
         * @return the dsl builder
         */
        default MicrometerEndpointBuilder micrometer(String path) {
            return MicrometerEndpointBuilderFactory.endpointBuilder("micrometer", path);
        }
        /**
         * Micrometer (camel-micrometer)
         * Collect various metrics directly from Camel routes using the
         * Micrometer library.
         * 
         * Category: monitoring
         * Since: 2.22
         * Maven coordinates: org.apache.camel:camel-micrometer
         * 
         * Syntax: <code>micrometer:metricsType:metricsName</code>
         * 
         * Path parameter: metricsType (required)
         * Type of metrics
         * There are 3 enums and the value can be one of: counter,
         * distribution_summary, timer
         * 
         * Path parameter: metricsName (required)
         * Name of metrics
         * 
         * Path parameter: tags
         * Tags of metrics
         * 
         * @param componentName to use a custom component name for the endpoint
         * instead of the default name
         * @param path metricsType:metricsName
         * @return the dsl builder
         */
        default MicrometerEndpointBuilder micrometer(
                String componentName,
                String path) {
            return MicrometerEndpointBuilderFactory.endpointBuilder(componentName, path);
        }
    }

    /**
     * The builder of headers' name for the Micrometer component.
     */
    public static class MicrometerHeaderNameBuilder {
        /**
         * The internal instance of the builder used to access to all the
         * methods representing the name of headers.
         */
        private static final MicrometerHeaderNameBuilder INSTANCE = new MicrometerHeaderNameBuilder();

        /**
         * Override timer action in URI.
         * 
         * The option is a: {@code
         * org.apache.camel.component.micrometer.MicrometerTimerAction} type.
         * 
         * Group: producer
         * 
         * @return the name of the header {@code MetricsTimerAction}.
         */
        public String metricsTimerAction() {
            return "MetricsTimerAction";
        }

        /**
         * Override histogram value in URI.
         * 
         * The option is a: {@code long} type.
         * 
         * Group: producer
         * 
         * @return the name of the header {@code MetricsHistogramValue}.
         */
        public String metricsHistogramValue() {
            return "MetricsHistogramValue";
        }

        /**
         * Override decrement value in URI.
         * 
         * The option is a: {@code Double} type.
         * 
         * Group: producer
         * 
         * @return the name of the header {@code MetricsCounterDecrement}.
         */
        public String metricsCounterDecrement() {
            return "MetricsCounterDecrement";
        }

        /**
         * Override increment value in URI.
         * 
         * The option is a: {@code Double} type.
         * 
         * Group: producer
         * 
         * @return the name of the header {@code MetricsCounterIncrement}.
         */
        public String metricsCounterIncrement() {
            return "MetricsCounterIncrement";
        }

        /**
         * Override name value in URI.
         * 
         * The option is a: {@code String} type.
         * 
         * Group: producer
         * 
         * @return the name of the header {@code MetricsName}.
         */
        public String metricsName() {
            return "MetricsName";
        }

        /**
         * Override description value in URI.
         * 
         * The option is a: {@code String} type.
         * 
         * Group: producer
         * 
         * @return the name of the header {@code MetricsDescription}.
         */
        public String metricsDescription() {
            return "MetricsDescription";
        }

        /**
         * To augment meter tags defined as URI parameters.
         * 
         * The option is a: {@code java.lang.Iterable<Tag>} type.
         * 
         * Group: producer
         * 
         * @return the name of the header {@code MetricsTags}.
         */
        public String metricsTags() {
            return "MetricsTags";
        }
    }
    static MicrometerEndpointBuilder endpointBuilder(
            String componentName,
            String path) {
        class MicrometerEndpointBuilderImpl extends AbstractEndpointBuilder implements MicrometerEndpointBuilder, AdvancedMicrometerEndpointBuilder {
            public MicrometerEndpointBuilderImpl(String path) {
                super(componentName, path);
            }
        }
        return new MicrometerEndpointBuilderImpl(path);
    }
}