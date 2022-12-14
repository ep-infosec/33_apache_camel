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
package org.apache.camel.component.cxf.converter;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Element;

import org.apache.camel.Converter;
import org.apache.camel.component.cxf.common.CxfPayload;
import org.apache.camel.converter.jaxp.DomConverter;

// This converter is used to show how to override the CxfPayload default toString converter
@Converter
public final class MyCxfCustomerConverter {

    private MyCxfCustomerConverter() {
        //Helper class
    }

    @Converter
    public static String cxfPayloadToString(final CxfPayload<?> payload) {
        DomConverter converter = new DomConverter();
        StringBuilder buf = new StringBuilder();
        for (Object element : payload.getBody()) {
            String elementString = "";
            try {
                elementString = converter.toString((Element) element, null);
            } catch (TransformerException e) {
                elementString = element.toString();
            }
            buf.append(elementString);
        }
        return buf.toString();
    }

}
