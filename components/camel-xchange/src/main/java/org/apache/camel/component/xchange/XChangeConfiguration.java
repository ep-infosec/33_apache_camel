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
package org.apache.camel.component.xchange;

import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;
import org.apache.camel.spi.UriPath;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

@UriParams
public class XChangeConfiguration {

    // Available service
    public enum XChangeService {
        marketdata,
        metadata,
        account
    }

    // Available methods
    public enum XChangeMethod {
        // Account service methods
        balances,
        fundingHistory,
        wallets,
        // Metadata service methods
        currencies,
        currencyMetaData,
        currencyPairs,
        currencyPairMetaData,
        // Marketdata service methods
        ticker
    }

    @Metadata(description = "The target currency", javaType = "org.knowm.xchange.currency.Currency")
    public static final String HEADER_CURRENCY = "Currency";
    @Metadata(description = "The target currency pair", javaType = "org.knowm.xchange.currency.CurrencyPair")
    public static final String HEADER_CURRENCY_PAIR = "CurrencyPair";

    @UriPath(description = "The exchange to connect to")
    @Metadata(required = true)
    private String name;
    @UriParam(description = "The service to call")
    @Metadata(required = true)
    private XChangeService service;
    @UriParam(description = "The method to execute")
    @Metadata(required = true)
    private XChangeMethod method;
    @UriParam(description = "The currency")
    private Currency currency;
    @UriParam(description = "The currency pair")
    private String currencyPair;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XChangeService getService() {
        return service;
    }

    public void setService(XChangeService service) {
        this.service = service;
    }

    public XChangeMethod getMethod() {
        return method;
    }

    public void setMethod(XChangeMethod method) {
        this.method = method;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setCurrency(String curr) {
        this.currency = Currency.getInstanceNoCreate(curr);
    }

    public CurrencyPair getAsCurrencyPair() {
        if (currencyPair != null) {
            return new CurrencyPair(currencyPair);
        }
        return null;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

}
