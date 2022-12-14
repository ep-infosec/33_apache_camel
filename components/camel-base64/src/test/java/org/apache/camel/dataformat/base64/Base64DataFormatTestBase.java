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
package org.apache.camel.dataformat.base64;

import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public abstract class Base64DataFormatTestBase extends CamelTestSupport {

    static final byte[] DECODED = {
            34, -76, 86, -124, -42, 77, -116, 92, 80,
            -23, 101, -55, 16, -117, 30, -123, -71, -59, 118, -22, -19, -127,
            -89, 0, -85, -21, 122, 102, 29, -18, 98, 92, -100, -108, 93, -57,
            3, 31, 125, -26, 102, -56, -55, -44, 37, -54, 99, 60, 87, 124,
            -128, -7, -122, -11, -89, 114, 35, -20, 4, -75, 85, 6, -108, 52,
            112, 37, -116, -38, 71, 82, -86, -42, 71, -95, 38, 1, 38, 85, -4,
            68, 47, 71, -111, -92, -15, 112, -17, 70, -70, -19, -110, -128,
            -26, -6, 55, 89, -3, 114, -97, 122, -53, 118, 56, -116, -31, -117,
            22, 69, -42, 103, 42, -54, 45, 79, 29, -22, 127, -84, 89, -43, 61,
            -55, 78, -11, 99, -106, -76, 32, -111, -121, 63, -108, -38, -54, 1,
            -52, -48, 109, -67, 55, 58, -57, 90, 115, -42, 3, -103, 114, -125,
            -124, 20, -95, -46, 127, 105, 38, -110, -83, 127, -18, 17, -59,
            -105, -112, -101, -10, 118, -46, 6, 12, -87, -108, 92, -20, 34,
            117, 65, 124, 41, 17, -59, 112, 5, -117, 28, 27, 11, 112, 126, 44,
            31, 30, -41, -100, -46, 50, -6, 6, -22, -119, 50, 14, -50, -62,
            -15, 4, -95, 33, -112, -105, 24, 121, 108, -71, 72, -121, -9, -88,
            -77, 81, -73, -63, -14, -86, 83, 64, -81, 52, 60, -16, 34, -77, 22,
            33, 23, -8, 27, -21, -65, 48, -68, -18, 94, -1, 77, -64, -104, 89,
            -104, 108, 26, -77, -62, -125, 80, -24, -6, 25, 40, 82, 60, 107,
            -125, -44, -84, -70, 2, 46, -75, 39, 41, 8, 48, 57, 123, -98, 111,
            92, -68, 119, -122, -20, 84, 106, -41, 31, -108, 20, 22, 0, 4, -33,
            38, 68, -97, 102, 80, -75, 91, -6, 109, -103, -26, -34, 91, -63,
            123, -14, -53, 68, 62, 49, 33, 77, -113, 114, 110, 94, -8, 50, 84,
            102, 17, -36, -105, -1, 12, 106, 42, 3, 88, 88, 24, 60, -73, -19,
            12, -85, -60, 83, 2, -63, -36, -127, 86, 45, 34, -116, 40, 90, 30,
            94, 125, -89, -109, 12, 108, 8, 122, -13, -123, -88, -31, 110, 66,
            -91, -41, -31, -3, 35, -79, -92, 119, 95, 67, -56, 10, 15, 34, -72,
            -106, 56, -108, -100, -94, 15, -90, 112, -3, 34, -88, 111, 98, 50,
            -90, 5, -110, -115, -89, -82, 29, -85, -81, -24, -36, -56, -95, 65,
            -122, -76, 84, -72, -36, 55, 91, -49, -95, -8, 83, -80, 100, 50,
            -36, 6, 107, -109, -65, 105, 68, 58, -11, 29, 104, -8, 3, -5, 87,
            -70, 125, 122, 110, -58, 24, -94, -121, -116, 31, -1, 57, 90, -111,
            -27, 90, -45, 125, 83, -11, -51, 63, 28, -35, 54, -49, 71, 0, -124,
            -76, -11, -66, -47, 0, -45, 48, -25, -24, -20, -13, -123, -113, -1,
            50, 29, -9, -39, -103, -126, -5, 26, -24, -7, -17, 104, -39, -82,
            -66, 118, 124, 26, -104, 59, 75, -71, 24, 3, 13, -33, -124, 114,
            -123, 16, -91, -15, 11, 94, -122, -41, -35, -108, -105, -20, -28,
            48, -43, -107, -15, 90, 34, 75, -75, 35, -100, -25, 34, -47, -111,
            -42, -15, 116, -78, -65, 11, -89, -28, 113, -114, 28, -87, -49,
            -47, -64, 17, -104, -75, 115, 123, 103, 81, 13, 27, -5, 63, 46,
            122, -39, -99, 21, 63, 18, -77, 22, 70, -42, -47, -63, -24, 64, -1,
            70, -74, 41, 38, -51, 56, -52, 10, -16, 26, 115, 14, -69, -105,
            -71, -14, -41, 101, -63, -102, 12, -92, 84, 122, 75, 80, 125, -43,
            -8, 94, 24, 88, 17, 43, 117, 118, -73, -80, -107, 2, -43, 104, 69,
            -85, 100, 20, 124, 36, -25, 121, -103, -55, 46, -108, -8, 112, 30,
            -99, -112, -45, 70, 50, -95, 39, 4, -113, -92, 108, -1, -37, 34,
            -54, -2, 61, -4, 84, -102, 68, 38, -25, 127, 57, 119, 9, 25, -51,
            -68, 35, -125, -43, -53, -59, -32, -23, -34, 97, -75, 73, 8, -61,
            -104, -29, -37, -100, 22, 99, 127, -104, 16, -78, -60, -77, -83,
            39, -122, 115, -16, 65, -75, 20, 47, 53, 65, 56, 55, 73, -52, 62,
            21, -47, 67, -80, 40, 43, -20, 58, -107, -82, 99, -50, 46, 41, 42,
            -85, -84, 9, 116, -80, 99, -56, 117, -18, 50, 37, 79, -12, 90, -65,
            104, 12, 111, 92, 72, 35, 70, -27, 103, 55, 109, 48, -97, 107, 84,
            57, 119, -102, 55, -123, -22, -50, 36, 58, -24, -51, -74, -12, 123,
            24, -48, 21, -7, -82, 34, 116, -45, 37, -64, -84, 60, 93, -8, -113,
            102, 20, 58, 112, -3, 64, -25, 24, 16, 4, -40, -1, -1, -43, -11,
            53, -98, -51, 64, -21, 52, 58, -123, 59, -5, 107, 23, 101, -61,
            127, -59, -100, -32, 29, -54, 97, 10, -88, 64, -3, 8, -9, -37,
            -120, 59, -55, -54, 7, 0, 115, 27, -10, 127, 35, -111, 29, 15,
            -109, -118, -102, -52, 27, 23, 36, 2, 89, -53, 103, 106, 70, -105,
            -24, 14, -51, -69, 89, -52, -104, 30, 115, 33, 73, 28, 22, -31,
            -74, 75, -101, 24, 62, 51, -51, 8, 110, 36, -100, 60, -54, -102,
            -87, -91, 44, 106, 14, -49, 18, 1, 109, -97, -82, 62, 54, 81, 63,
            106, 68, -57, -126, 4, 101, -53, 107, 92, 50, 33, 43, 120, 24,
            -114, -94, 58, 119, -16, 76, 36, 73, -3, 33, 59, 9, 90, -60, 126,
            103, 102, 68, -23, 73, -92, 2, 71, 125, 73, 80, 32, -102, -75, 105,
            109, -26, 76, 78, 115, 78, 96, 50, -125, 42, 113, 69, 64, -62,
            -104, 15, -98, 99, -36, 29, 10, 39, 5, -89, -90, 41, -75, -48,
            -124, 43, 115, -10, -19, 12, -39, -79, 32, 18, 0, 28, -99, -26, 60,
            71, 50, 34, 1, -111, -36, 6, -50, 61, 121, -45, 92, 89, -18, 17,
            75, 36, 53, -61, 77 };

    protected Base64DataFormat format = new Base64DataFormat();

    @EndpointInject("mock:result")
    private MockEndpoint result;

    protected void runEncoderTest(byte[] raw, byte[] expected) throws Exception {
        result.setExpectedMessageCount(1);

        template.sendBody("direct:startEncode", raw);

        MockEndpoint.assertIsSatisfied(context);

        byte[] encoded = result.getReceivedExchanges().get(0).getIn().getBody(byte[].class);
        assertArrayEquals(expected, encoded);
    }

    protected void runDecoderTest(byte[] encoded, byte[] expected) throws Exception {
        result.setExpectedMessageCount(1);

        template.sendBody("direct:startDecode", encoded);

        MockEndpoint.assertIsSatisfied(context);

        byte[] decoded = result.getReceivedExchanges().get(0).getIn().getBody(byte[].class);
        assertArrayEquals(expected, decoded);
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {

            @Override
            public void configure() {
                from("direct:startEncode").marshal(format).to("mock:result");

                from("direct:startDecode").unmarshal(format).to("mock:result");
            }
        };
    }

}
