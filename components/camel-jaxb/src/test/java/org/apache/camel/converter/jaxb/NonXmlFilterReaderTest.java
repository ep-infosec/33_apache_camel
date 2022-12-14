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
package org.apache.camel.converter.jaxb;

import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NonXmlFilterReaderTest {
    private NonXmlFilterReader nonXmlFilterReader;
    @Mock
    private NonXmlCharFilterer nonXmlCharFiltererMock;
    @Mock
    private Reader readerMock;

    @BeforeEach
    public void setUp() {
        nonXmlFilterReader = new NonXmlFilterReader(readerMock);
        nonXmlFilterReader.nonXmlCharFilterer = nonXmlCharFiltererMock;
    }

    @Test
    public void testRead() throws IOException {
        char[] buffer = new char[10];

        when(readerMock.read(same(buffer), eq(3), eq(5))).thenAnswer(new Answer<Integer>() {

            public Integer answer(InvocationOnMock invocation) throws Throwable {
                try (ConstantReader reader = new ConstantReader(new char[] { 'a', 'b', 'c' })) {
                    Object[] args = invocation.getArguments();
                    return reader.read((char[]) args[0], (Integer) args[1], (Integer) args[2]);
                }
            }
        });

        int result = nonXmlFilterReader.read(buffer, 3, 5);

        verify(readerMock).read(same(buffer), eq(3), eq(5));
        verify(nonXmlCharFiltererMock).filter(same(buffer), eq(3), eq(3));

        assertEquals(3, result, "Unexpected number of chars read");
        assertArrayEquals(new char[] { 0, 0, 0, 'a', 'b', 'c', 0, 0, 0, 0 }, buffer, "Wrong buffer contents");
    }

    @Test
    public void testReadEOS() throws IOException {
        char[] buffer = new char[10];

        when(readerMock.read(any(char[].class), anyInt(), anyInt())).thenReturn(-1);

        int result = nonXmlFilterReader.read(buffer, 3, 5);

        assertEquals(-1, result, "Unexpected number of chars read");
        assertArrayEquals(new char[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, buffer, "Buffer should not have been affected");
    }

    static class ConstantReader extends Reader {
        private char[] constant;

        ConstantReader(char[] constant) {
            this.constant = constant;
        }

        @Override
        public void close() {
        }

        @Override
        public int read(char[] cbuf, int off, int len) {
            int length = Math.min(len, constant.length);
            System.arraycopy(constant, 0, cbuf, off, length);
            return length;
        }

    }
}
