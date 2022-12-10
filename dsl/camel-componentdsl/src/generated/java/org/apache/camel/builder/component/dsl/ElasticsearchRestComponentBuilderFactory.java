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
import org.apache.camel.component.elasticsearch.ElasticsearchComponent;

/**
 * Send requests to ElasticSearch via REST API
 * 
 * Generated by camel-package-maven-plugin - do not edit this file!
 */
@Generated("org.apache.camel.maven.packaging.ComponentDslMojo")
public interface ElasticsearchRestComponentBuilderFactory {

    /**
     * Elasticsearch Rest (camel-elasticsearch-rest)
     * Send requests to ElasticSearch via REST API
     * 
     * Category: search,monitoring
     * Since: 2.21
     * Maven coordinates: org.apache.camel:camel-elasticsearch-rest
     * 
     * @return the dsl builder
     */
    @Deprecated
    static ElasticsearchRestComponentBuilder elasticsearchRest() {
        return new ElasticsearchRestComponentBuilderImpl();
    }

    /**
     * Builder for the Elasticsearch Rest component.
     */
    interface ElasticsearchRestComponentBuilder
            extends
                ComponentBuilder<ElasticsearchComponent> {
        /**
         * The time in ms to wait before connection will timeout.
         * 
         * The option is a: &lt;code&gt;int&lt;/code&gt; type.
         * 
         * Default: 30000
         * Group: producer
         * 
         * @param connectionTimeout the value to set
         * @return the dsl builder
         */
        default ElasticsearchRestComponentBuilder connectionTimeout(
                int connectionTimeout) {
            doSetProperty("connectionTimeout", connectionTimeout);
            return this;
        }
        /**
         * Comma separated list with ip:port formatted remote transport
         * addresses to use. The ip and port options must be left blank for
         * hostAddresses to be considered instead.
         * 
         * The option is a: &lt;code&gt;java.lang.String&lt;/code&gt; type.
         * 
         * Group: producer
         * 
         * @param hostAddresses the value to set
         * @return the dsl builder
         */
        default ElasticsearchRestComponentBuilder hostAddresses(
                java.lang.String hostAddresses) {
            doSetProperty("hostAddresses", hostAddresses);
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
        default ElasticsearchRestComponentBuilder lazyStartProducer(
                boolean lazyStartProducer) {
            doSetProperty("lazyStartProducer", lazyStartProducer);
            return this;
        }
        /**
         * The time in ms before retry.
         * 
         * The option is a: &lt;code&gt;int&lt;/code&gt; type.
         * 
         * Default: 30000
         * Group: producer
         * 
         * @param maxRetryTimeout the value to set
         * @return the dsl builder
         */
        default ElasticsearchRestComponentBuilder maxRetryTimeout(
                int maxRetryTimeout) {
            doSetProperty("maxRetryTimeout", maxRetryTimeout);
            return this;
        }
        /**
         * The timeout in ms to wait before the socket will timeout.
         * 
         * The option is a: &lt;code&gt;int&lt;/code&gt; type.
         * 
         * Default: 30000
         * Group: producer
         * 
         * @param socketTimeout the value to set
         * @return the dsl builder
         */
        default ElasticsearchRestComponentBuilder socketTimeout(
                int socketTimeout) {
            doSetProperty("socketTimeout", socketTimeout);
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
        default ElasticsearchRestComponentBuilder autowiredEnabled(
                boolean autowiredEnabled) {
            doSetProperty("autowiredEnabled", autowiredEnabled);
            return this;
        }
        /**
         * To use an existing configured Elasticsearch client, instead of
         * creating a client per endpoint. This allows to customize the client
         * with specific settings.
         * 
         * The option is a:
         * &lt;code&gt;org.elasticsearch.client.RestClient&lt;/code&gt; type.
         * 
         * Group: advanced
         * 
         * @param client the value to set
         * @return the dsl builder
         */
        default ElasticsearchRestComponentBuilder client(
                org.elasticsearch.client.RestClient client) {
            doSetProperty("client", client);
            return this;
        }
        /**
         * Enable automatically discover nodes from a running Elasticsearch
         * cluster. If this option is used in conjunction with Spring Boot then
         * it's managed by the Spring Boot configuration (see: Disable Sniffer
         * in Spring Boot).
         * 
         * The option is a: &lt;code&gt;boolean&lt;/code&gt; type.
         * 
         * Default: false
         * Group: advanced
         * 
         * @param enableSniffer the value to set
         * @return the dsl builder
         */
        default ElasticsearchRestComponentBuilder enableSniffer(
                boolean enableSniffer) {
            doSetProperty("enableSniffer", enableSniffer);
            return this;
        }
        /**
         * The delay of a sniff execution scheduled after a failure (in
         * milliseconds).
         * 
         * The option is a: &lt;code&gt;int&lt;/code&gt; type.
         * 
         * Default: 60000
         * Group: advanced
         * 
         * @param sniffAfterFailureDelay the value to set
         * @return the dsl builder
         */
        default ElasticsearchRestComponentBuilder sniffAfterFailureDelay(
                int sniffAfterFailureDelay) {
            doSetProperty("sniffAfterFailureDelay", sniffAfterFailureDelay);
            return this;
        }
        /**
         * The interval between consecutive ordinary sniff executions in
         * milliseconds. Will be honoured when sniffOnFailure is disabled or
         * when there are no failures between consecutive sniff executions.
         * 
         * The option is a: &lt;code&gt;int&lt;/code&gt; type.
         * 
         * Default: 300000
         * Group: advanced
         * 
         * @param snifferInterval the value to set
         * @return the dsl builder
         */
        default ElasticsearchRestComponentBuilder snifferInterval(
                int snifferInterval) {
            doSetProperty("snifferInterval", snifferInterval);
            return this;
        }
        /**
         * Enable SSL.
         * 
         * The option is a: &lt;code&gt;boolean&lt;/code&gt; type.
         * 
         * Default: false
         * Group: security
         * 
         * @param enableSSL the value to set
         * @return the dsl builder
         */
        default ElasticsearchRestComponentBuilder enableSSL(boolean enableSSL) {
            doSetProperty("enableSSL", enableSSL);
            return this;
        }
        /**
         * Password for authenticate.
         * 
         * The option is a: &lt;code&gt;java.lang.String&lt;/code&gt; type.
         * 
         * Group: security
         * 
         * @param password the value to set
         * @return the dsl builder
         */
        default ElasticsearchRestComponentBuilder password(
                java.lang.String password) {
            doSetProperty("password", password);
            return this;
        }
        /**
         * Basic authenticate user.
         * 
         * The option is a: &lt;code&gt;java.lang.String&lt;/code&gt; type.
         * 
         * Group: security
         * 
         * @param user the value to set
         * @return the dsl builder
         */
        default ElasticsearchRestComponentBuilder user(java.lang.String user) {
            doSetProperty("user", user);
            return this;
        }
    }

    class ElasticsearchRestComponentBuilderImpl
            extends
                AbstractComponentBuilder<ElasticsearchComponent>
            implements
                ElasticsearchRestComponentBuilder {
        @Override
        protected ElasticsearchComponent buildConcreteComponent() {
            return new ElasticsearchComponent();
        }
        @Override
        protected boolean setPropertyOnComponent(
                Component component,
                String name,
                Object value) {
            switch (name) {
            case "connectionTimeout": ((ElasticsearchComponent) component).setConnectionTimeout((int) value); return true;
            case "hostAddresses": ((ElasticsearchComponent) component).setHostAddresses((java.lang.String) value); return true;
            case "lazyStartProducer": ((ElasticsearchComponent) component).setLazyStartProducer((boolean) value); return true;
            case "maxRetryTimeout": ((ElasticsearchComponent) component).setMaxRetryTimeout((int) value); return true;
            case "socketTimeout": ((ElasticsearchComponent) component).setSocketTimeout((int) value); return true;
            case "autowiredEnabled": ((ElasticsearchComponent) component).setAutowiredEnabled((boolean) value); return true;
            case "client": ((ElasticsearchComponent) component).setClient((org.elasticsearch.client.RestClient) value); return true;
            case "enableSniffer": ((ElasticsearchComponent) component).setEnableSniffer((boolean) value); return true;
            case "sniffAfterFailureDelay": ((ElasticsearchComponent) component).setSniffAfterFailureDelay((int) value); return true;
            case "snifferInterval": ((ElasticsearchComponent) component).setSnifferInterval((int) value); return true;
            case "enableSSL": ((ElasticsearchComponent) component).setEnableSSL((boolean) value); return true;
            case "password": ((ElasticsearchComponent) component).setPassword((java.lang.String) value); return true;
            case "user": ((ElasticsearchComponent) component).setUser((java.lang.String) value); return true;
            default: return false;
            }
        }
    }
}