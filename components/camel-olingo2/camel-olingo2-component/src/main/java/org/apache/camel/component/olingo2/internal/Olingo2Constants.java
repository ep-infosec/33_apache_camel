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
package org.apache.camel.component.olingo2.internal;

import org.apache.camel.spi.Metadata;

/**
 * Constants for Olingo2 component.
 */
public interface Olingo2Constants {

    // prefix for parameters when passed as exchange header properties
    String PROPERTY_PREFIX = "CamelOlingo2.";

    // thread profile name for this component
    String THREAD_PROFILE_NAME = "CamelOlingo2";

    @Metadata(label = "producer", description = "The response Http headers", javaType = "Map<String, String>")
    String RESPONSE_HTTP_HEADERS = PROPERTY_PREFIX + "responseHttpHeaders";
}
