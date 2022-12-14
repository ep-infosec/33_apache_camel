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
package org.apache.camel.component.sap.netweaver;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Producer;
import org.apache.camel.support.DefaultProducer;
import org.apache.camel.support.ExchangeHelper;
import org.apache.camel.support.service.ServiceHelper;
import org.apache.camel.util.URISupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetWeaverProducer extends DefaultProducer {

    private static final Logger LOG = LoggerFactory.getLogger(NetWeaverProducer.class);

    private Producer http;

    public NetWeaverProducer(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public NetWeaverEndpoint getEndpoint() {
        return (NetWeaverEndpoint) super.getEndpoint();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String command = ExchangeHelper.getMandatoryHeader(exchange, NetWeaverConstants.COMMAND, String.class);

        Exchange httpExchange = getEndpoint().createExchange();
        httpExchange.getIn().setHeader(NetWeaverConstants.HTTP_PATH, command);
        if (getEndpoint().isJson()) {
            httpExchange.getIn().setHeader(NetWeaverConstants.ACCEPT, "application/json");
        }

        LOG.debug("Calling SAP Net-Weaver {} with command {}", http, command);
        http.process(httpExchange);

        String data = httpExchange.getOut().getBody(String.class);

        if (data != null && getEndpoint().isJsonAsMap() && getEndpoint().isJson()) {
            // map json string to json map
            ObjectMapper mapper = new ObjectMapper();
            Map<?, ?> map = mapper.readValue(data, Map.class);

            // if we only have one entry in the map, then put that as root (as it tends to return a single instance "d"
            if (map.size() == 1 && getEndpoint().isFlatternMap()) {
                exchange.getIn().setBody(map.values().iterator().next());
            } else {
                exchange.getIn().setBody(map);
            }
        } else {
            // store data as is
            exchange.getIn().setBody(data);
        }
    }

    @Override
    protected void doStart() throws Exception {
        String url = getEndpoint().getUrl() + "?authUsername=" + getEndpoint().getUsername() + "&authPassword="
                     + getEndpoint().getPassword() + "&authMethod=Basic";
        if (LOG.isInfoEnabled()) {
            LOG.info("Creating NetWeaverProducer using url: {}", URISupport.sanitizeUri(url));
        }

        http = getEndpoint().getCamelContext().getEndpoint(url).createProducer();
        ServiceHelper.startService(http);
    }

    @Override
    protected void doStop() throws Exception {
        ServiceHelper.stopService(http);
    }
}
