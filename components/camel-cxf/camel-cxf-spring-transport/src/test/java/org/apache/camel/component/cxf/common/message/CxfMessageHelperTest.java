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
package org.apache.camel.component.cxf.common.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.dom.DOMSource;

import org.apache.camel.component.cxf.transport.header.CxfHeaderFilterStrategy;
import org.apache.camel.component.cxf.transport.message.CxfMessageHelper;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.HeaderFilterStrategy;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.util.IOHelper;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.staxutils.StaxUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CxfMessageHelperTest {
    private static final String REQUEST_STRING = "<testMethod xmlns=\"http://camel.apache.org/testService\"/>";
    private DefaultCamelContext context = new DefaultCamelContext();

    // setup the default context for testing
    @Test
    public void testGetCxfInMessage() throws Exception {
        HeaderFilterStrategy headerFilterStrategy = new CxfHeaderFilterStrategy();
        org.apache.camel.Exchange exchange = new DefaultExchange(context);
        // String
        exchange.getIn().setBody("hello world");
        org.apache.cxf.message.Message message = CxfMessageHelper.getCxfInMessage(
                headerFilterStrategy, exchange, false);
        // test message
        InputStream is = message.getContent(InputStream.class);
        assertNotNull(is, "The input stream should not be null");
        assertEquals("hello world", toString(is), "Don't get the right message");

        // DOMSource
        URL request = this.getClass().getResource("RequestBody.xml");
        File requestFile = new File(request.toURI());
        FileInputStream inputStream = new FileInputStream(requestFile);
        XMLStreamReader xmlReader = StaxUtils.createXMLStreamReader(inputStream);
        DOMSource source = new DOMSource(StaxUtils.read(xmlReader));
        exchange.getIn().setBody(source);
        message = CxfMessageHelper.getCxfInMessage(headerFilterStrategy, exchange, false);
        is = message.getContent(InputStream.class);
        assertNotNull(is, "The input stream should not be null");
        assertEquals(REQUEST_STRING, toString(is), "Don't get the right message");

        // File
        exchange.getIn().setBody(requestFile);
        message = CxfMessageHelper.getCxfInMessage(headerFilterStrategy, exchange, false);
        is = message.getContent(InputStream.class);
        assertNotNull(is, "The input stream should not be null");
        assertEquals(REQUEST_STRING, toString(is), "Don't get the right message");

        // transport header's case insensitiveness
        // String
        exchange.getIn().setBody("hello world");
        exchange.getIn().setHeader("soapAction", "urn:hello:world");
        message = CxfMessageHelper.getCxfInMessage(headerFilterStrategy, exchange, false);
        // test message
        Map<String, List<String>> headers = CastUtils.cast((Map<?, ?>) message.get(Message.PROTOCOL_HEADERS));

        // verify there is no duplicate
        assertNotNull(headers, "The headers must be present");
        assertEquals(1, headers.size(), "There must be one header entry");

        // verify the soapaction can be retrieved in case-insensitive ways
        verifyHeader(headers, "soapaction", "urn:hello:world");
        verifyHeader(headers, "SoapAction", "urn:hello:world");
        verifyHeader(headers, "SOAPAction", "urn:hello:world");
    }

    private void verifyHeader(Map<String, List<String>> headers, String name, String value) {
        List<String> values = headers.get(name);
        assertTrue(values != null && values.size() == 1, "The entry must be available");
        assertEquals(values.get(0), value, "The value must match");
    }

    private String toString(InputStream is) throws IOException {
        StringBuilder out = new StringBuilder();
        CachedOutputStream os = new CachedOutputStream();
        IOHelper.copy(is, os);
        is.close();
        os.writeCacheTo(out);
        return out.toString().replaceAll("(?s)<\\?.*\\?>", "").replaceAll("(?s)<!--.*-->", "").replaceAll("(\\r)?\\n", "");

    }

}
