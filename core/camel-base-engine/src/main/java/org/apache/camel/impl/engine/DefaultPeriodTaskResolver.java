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
package org.apache.camel.impl.engine;

import java.util.Optional;

import org.apache.camel.spi.FactoryFinder;
import org.apache.camel.spi.PeriodTaskResolver;

public class DefaultPeriodTaskResolver implements PeriodTaskResolver {

    private final FactoryFinder finder;

    public DefaultPeriodTaskResolver(FactoryFinder finder) {
        this.finder = finder;
    }

    @Override
    public Optional<Object> newInstance(String key) {
        return finder.newInstance(key);
    }

    @Override
    public <T> Optional<T> newInstance(String key, Class<T> type) {
        return finder.newInstance(key, type);
    }
}
