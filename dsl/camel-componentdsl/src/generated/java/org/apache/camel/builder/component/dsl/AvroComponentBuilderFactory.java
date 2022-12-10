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
package org.apache.camel.builder.component.dsl;

import javax.annotation.Generated;
import org.apache.camel.Component;
import org.apache.camel.builder.component.AbstractComponentBuilder;
import org.apache.camel.builder.component.ComponentBuilder;
import org.apache.camel.component.avro.AvroComponent;

/**
 * Produce or consume Apache Avro RPC services.
 * 
 * Generated by camel-package-maven-plugin - do not edit this file!
 */
@Generated("org.apache.camel.maven.packaging.ComponentDslMojo")
public interface AvroComponentBuilderFactory {

    /**
     * Avro RPC (camel-avro-rpc)
     * Produce or consume Apache Avro RPC services.
     * 
     * Category: rpc
     * Since: 2.10
     * Maven coordinates: org.apache.camel:camel-avro-rpc
     * 
     * @return the dsl builder
     */
    static AvroComponentBuilder avro() {
        return new AvroComponentBuilderImpl();
    }

    /**
     * Builder for the Avro RPC component.
     */
    interface AvroComponentBuilder extends ComponentBuilder<AvroComponent> {
        /**
         * Avro protocol to use.
         * 
         * The option is a: &lt;code&gt;org.apache.avro.Protocol&lt;/code&gt;
         * type.
         * 
         * Group: common
         * 
         * @param protocol the value to set
         * @return the dsl builder
         */
        default AvroComponentBuilder protocol(org.apache.avro.Protocol protocol) {
            doSetProperty("protocol", protocol);
            return this;
        }
        /**
         * Avro protocol to use defined by the FQN class name.
         * 
         * The option is a: &lt;code&gt;java.lang.String&lt;/code&gt; type.
         * 
         * Group: common
         * 
         * @param protocolClassName the value to set
         * @return the dsl builder
         */
        default AvroComponentBuilder protocolClassName(
                java.lang.String protocolClassName) {
            doSetProperty("protocolClassName", protocolClassName);
            return this;
        }
        /**
         * Avro protocol location.
         * 
         * The option is a: &lt;code&gt;java.lang.String&lt;/code&gt; type.
         * 
         * Group: common
         * 
         * @param protocolLocation the value to set
         * @return the dsl builder
         */
        default AvroComponentBuilder protocolLocation(
                java.lang.String protocolLocation) {
            doSetProperty("protocolLocation", protocolLocation);
            return this;
        }
        /**
         * If protocol object provided is reflection protocol. Should be used
         * only with protocol parameter because for protocolClassName protocol
         * type will be auto detected.
         * 
         * The option is a: &lt;code&gt;boolean&lt;/code&gt; type.
         * 
         * Default: false
         * Group: common
         * 
         * @param reflectionProtocol the value to set
         * @return the dsl builder
         */
        default AvroComponentBuilder reflectionProtocol(
                boolean reflectionProtocol) {
            doSetProperty("reflectionProtocol", reflectionProtocol);
            return this;
        }
        /**
         * If true, consumer parameter won't be wrapped into array. Will fail if
         * protocol specifies more then 1 parameter for the message.
         * 
         * The option is a: &lt;code&gt;boolean&lt;/code&gt; type.
         * 
         * Default: false
         * Group: common
         * 
         * @param singleParameter the value to set
         * @return the dsl builder
         */
        default AvroComponentBuilder singleParameter(boolean singleParameter) {
            doSetProperty("singleParameter", singleParameter);
            return this;
        }
        /**
         * Authority to use (username and password).
         * 
         * The option is a: &lt;code&gt;java.lang.String&lt;/code&gt; type.
         * 
         * Group: common
         * 
         * @param uriAuthority the value to set
         * @return the dsl builder
         */
        default AvroComponentBuilder uriAuthority(java.lang.String uriAuthority) {
            doSetProperty("uriAuthority", uriAuthority);
            return this;
        }
        /**
         * Allows for bridging the consumer to the Camel routing Error Handler,
         * which mean any exceptions occurred while the consumer is trying to
         * pickup incoming messages, or the likes, will now be processed as a
         * message and handled by the routing Error Handler. By default the
         * consumer will use the org.apache.camel.spi.ExceptionHandler to deal
         * with exceptions, that will be logged at WARN or ERROR level and
         * ignored.
         * 
         * The option is a: &lt;code&gt;boolean&lt;/code&gt; type.
         * 
         * Default: false
         * Group: consumer
         * 
         * @param bridgeErrorHandler the value to set
         * @return the dsl builder
         */
        default AvroComponentBuilder bridgeErrorHandler(
                boolean bridgeErrorHandler) {
            doSetProperty("bridgeErrorHandler", bridgeErrorHandler);
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
         * The option is a: &lt;code&gt;boolean&lt;/code&gt; type.
         * 
         * Default: false
         * Group: producer
         * 
         * @param lazyStartProducer the value to set
         * @return the dsl builder
         */
        default AvroComponentBuilder lazyStartProducer(boolean lazyStartProducer) {
            doSetProperty("lazyStartProducer", lazyStartProducer);
            return this;
        }
        /**
         * Whether autowiring is enabled. This is used for automatic autowiring
         * options (the option must be marked as autowired) by looking up in the
         * registry to find if there is a single instance of matching type,
         * which then gets configured on the component. This can be used for
         * automatic configuring JDBC data sources, JMS connection factories,
         * AWS Clients, etc.
         * 
         * The option is a: &lt;code&gt;boolean&lt;/code&gt; type.
         * 
         * Default: true
         * Group: advanced
         * 
         * @param autowiredEnabled the value to set
         * @return the dsl builder
         */
        default AvroComponentBuilder autowiredEnabled(boolean autowiredEnabled) {
            doSetProperty("autowiredEnabled", autowiredEnabled);
            return this;
        }
        /**
         * To use a shared AvroConfiguration to configure options once.
         * 
         * The option is a:
         * &lt;code&gt;org.apache.camel.component.avro.AvroConfiguration&lt;/code&gt; type.
         * 
         * Group: advanced
         * 
         * @param configuration the value to set
         * @return the dsl builder
         */
        default AvroComponentBuilder configuration(
                org.apache.camel.component.avro.AvroConfiguration configuration) {
            doSetProperty("configuration", configuration);
            return this;
        }
    }

    class AvroComponentBuilderImpl
            extends
                AbstractComponentBuilder<AvroComponent>
            implements
                AvroComponentBuilder {
        @Override
        protected AvroComponent buildConcreteComponent() {
            return new AvroComponent();
        }
        private org.apache.camel.component.avro.AvroConfiguration getOrCreateConfiguration(
                org.apache.camel.component.avro.AvroComponent component) {
            if (component.getConfiguration() == null) {
                component.setConfiguration(new org.apache.camel.component.avro.AvroConfiguration());
            }
            return component.getConfiguration();
        }
        @Override
        protected boolean setPropertyOnComponent(
                Component component,
                String name,
                Object value) {
            switch (name) {
            case "protocol": getOrCreateConfiguration((AvroComponent) component).setProtocol((org.apache.avro.Protocol) value); return true;
            case "protocolClassName": getOrCreateConfiguration((AvroComponent) component).setProtocolClassName((java.lang.String) value); return true;
            case "protocolLocation": getOrCreateConfiguration((AvroComponent) component).setProtocolLocation((java.lang.String) value); return true;
            case "reflectionProtocol": getOrCreateConfiguration((AvroComponent) component).setReflectionProtocol((boolean) value); return true;
            case "singleParameter": getOrCreateConfiguration((AvroComponent) component).setSingleParameter((boolean) value); return true;
            case "uriAuthority": getOrCreateConfiguration((AvroComponent) component).setUriAuthority((java.lang.String) value); return true;
            case "bridgeErrorHandler": ((AvroComponent) component).setBridgeErrorHandler((boolean) value); return true;
            case "lazyStartProducer": ((AvroComponent) component).setLazyStartProducer((boolean) value); return true;
            case "autowiredEnabled": ((AvroComponent) component).setAutowiredEnabled((boolean) value); return true;
            case "configuration": ((AvroComponent) component).setConfiguration((org.apache.camel.component.avro.AvroConfiguration) value); return true;
            default: return false;
            }
        }
    }
}