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
package org.apache.camel.component.cxf.transport.message;

import org.apache.camel.Exchange;
import org.apache.camel.spi.HeaderFilterStrategy;
import org.apache.cxf.message.Message;

/**
 * A Strategy to bind a Camel exchange to a CXF message used by {@link CxfBeanDestination}.
 */
public interface CxfMessageMapper {

    /**
     * Create a CXF {@link Message} from a Camel exchange.
     */
    Message createCxfMessageFromCamelExchange(
            Exchange camelExchange,
            HeaderFilterStrategy headerFilterStrategy);

    /**
     * Given a CXF out/response Message, this method propagates response headers to a Camel exchange.
     */
    void propagateResponseHeadersToCamel(
            Message cxfMessage, Exchange camelExchange,
            HeaderFilterStrategy headerFilterStrategy);

}
