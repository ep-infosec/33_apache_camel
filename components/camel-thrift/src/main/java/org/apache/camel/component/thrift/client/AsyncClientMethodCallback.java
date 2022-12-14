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
package org.apache.camel.component.thrift.client;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.thrift.async.AsyncMethodCallback;

@SuppressWarnings("rawtypes")
public class AsyncClientMethodCallback implements AsyncMethodCallback {
    private final Exchange exchange;
    private final AsyncCallback callback;

    public AsyncClientMethodCallback(Exchange exchange, AsyncCallback callback) {
        this.exchange = exchange;
        this.callback = callback;
    }

    @Override
    public void onComplete(Object response) {
        exchange.getMessage().setHeaders(exchange.getIn().getHeaders());
        if (response == null) {
            exchange.getMessage().setBody(response);
        } else {
            exchange.getMessage().setBody(response, response.getClass());
        }
        callback.done(false);
    }

    @Override
    public void onError(Exception exception) {
        exchange.setException(exception);
        callback.done(false);
    }
}
