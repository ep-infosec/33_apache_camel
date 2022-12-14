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
package org.apache.camel.component.jcache;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.camel.CamelContext;
import org.apache.camel.support.CamelContextHelper;

public final class JCacheHelper {
    private JCacheHelper() {
    }

    public static <K, V> JCacheManager<K, V> createManager(CamelContext camelContext, JCacheConfiguration configuration) {
        JCacheManagerFactory factory = CamelContextHelper.findSingleByType(camelContext, JCacheManagerFactory.class);
        if (factory == null) {
            factory = new DefaultJCacheManagerFactory();
        }
        return factory.createManager(configuration);
    }

    @SuppressWarnings("unchecked")
    public static <T> T tcclProxy(final T instance, Class<T> type, final ClassLoader classLoader) {
        return (T) Proxy.newProxyInstance(
                JCacheHelper.class.getClassLoader(),
                new Class<?>[] {
                        type
                },
                (Object proxy, Method method, Object[] args) -> {
                    final ClassLoader tccl = Thread.currentThread().getContextClassLoader();
                    try {
                        Thread.currentThread().setContextClassLoader(classLoader);
                        return method.invoke(instance, args);
                    } finally {
                        Thread.currentThread().setContextClassLoader(tccl);
                    }
                });
    }

}
