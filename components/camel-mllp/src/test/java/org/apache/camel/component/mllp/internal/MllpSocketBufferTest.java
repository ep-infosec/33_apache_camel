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
package org.apache.camel.component.mllp.internal;

import org.apache.camel.component.mllp.MllpProtocolConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for the MllpSocketBuffer class.
 */
public class MllpSocketBufferTest extends SocketBufferTestSupport {
    /**
     * Description of test.
     *
     */
    @Test
    public void testConstructorWithNullEndpoing() {
        try {
            new MllpSocketBuffer(null);
            fail("Constructor should have thrown an exception with a null Endpoint argument");
        } catch (IllegalArgumentException expectedEx) {
            assertEquals("MllpEndpoint cannot be null", expectedEx.getMessage());
        }
    }

    /**
     * Description of test.
     *
     * @throws Exception in the event of a test error.
     */
    @Test
    public void testToHl7StringWithRequiredEndOfData() throws Exception {
        assertNull(instance.toHl7String());

        instance.write(buildTestBytes(true, true, true));
        assertEquals(TEST_HL7_MESSAGE, instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(true, true, false));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(true, false, false));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(false, true, true));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(false, true, false));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(false, false, false));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(null, true, true, true));
        assertEquals("", instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(null, true, true, false));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(null, true, false, false));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(null, false, true, true));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(null, false, true, false));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(null, false, false, false));
        assertNull(instance.toHl7String());
    }

    /**
     * Description of test.
     *
     * @throws Exception in the event of a test error.
     */
    @Test
    public void testToHl7StringWithOptionalEndOfData() throws Exception {
        endpoint.setRequireEndOfData(false);
        assertNull(instance.toHl7String());

        instance.write(buildTestBytes(true, true, true));
        assertEquals(TEST_HL7_MESSAGE, instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(true, true, false));
        assertEquals(TEST_HL7_MESSAGE, instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(true, false, false));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(false, true, true));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(false, true, false));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(false, false, false));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(null, true, true, true));
        assertEquals("", instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(null, true, true, false));
        assertEquals("", instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(null, true, false, false));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(null, false, true, true));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(null, false, true, false));
        assertNull(instance.toHl7String());

        instance.reset();
        instance.write(buildTestBytes(null, false, false, false));
        assertNull(instance.toHl7String());
    }

    /**
     * Description of test.
     *
     * @throws Exception in the event of a test error.
     */
    @Test
    public void testToHl7StringWithInvalidCharset() throws Exception {
        instance.write(buildTestBytes(true, true, true));
        assertEquals(TEST_HL7_MESSAGE, instance.toHl7String("FOO"));
    }

    /**
     * Description of test.
     *
     * @throws Exception in the event of a test error.
     */
    @Test
    public void testToMllpPayloadWithRequiredEndOfData() throws Exception {
        assertNull(instance.toMllpPayload());

        instance.write(buildTestBytes(true, true, true));
        assertArrayEquals(TEST_HL7_MESSAGE.getBytes(), instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(true, true, false));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(true, false, false));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(false, true, true));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(false, true, false));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(false, false, false));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(null, true, true, true));
        assertArrayEquals(new byte[0], instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(null, true, true, false));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(null, true, false, false));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(null, false, true, true));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(null, false, true, false));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(null, false, false, false));
        assertNull(instance.toMllpPayload());
    }

    /**
     * Description of test.
     *
     * @throws Exception in the event of a test error.
     */
    @Test
    public void testToMllpPayloadWithOptionalEndOfData() throws Exception {
        endpoint.setRequireEndOfData(false);
        assertNull(instance.toMllpPayload());

        instance.write(buildTestBytes(true, true, true));
        assertArrayEquals(TEST_HL7_MESSAGE.getBytes(), instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(true, true, false));
        assertArrayEquals(TEST_HL7_MESSAGE.getBytes(), instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(true, false, false));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(false, true, true));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(false, true, false));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(false, false, false));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(null, true, true, true));
        assertArrayEquals(new byte[0], instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(null, true, true, false));
        assertArrayEquals(new byte[0], instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(null, true, false, false));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(null, false, true, true));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(null, false, true, false));
        assertNull(instance.toMllpPayload());

        instance.reset();
        instance.write(buildTestBytes(null, false, false, false));
        assertNull(instance.toMllpPayload());
    }

    /**
     * Description of test.
     *
     */
    @Test
    public void testGetStartOfBlockIndex() {
        int expected = -1;
        assertEquals(expected, instance.getStartOfBlockIndex(), "Unexpected initial value");

        expected = 0;
        instance.startOfBlockIndex = expected;
        assertEquals(expected, instance.getStartOfBlockIndex());

        expected = 5;
        instance.startOfBlockIndex = expected;
        assertEquals(expected, instance.getStartOfBlockIndex());
    }

    /**
     * Description of test.
     *
     */
    @Test
    public void tesGgetEndOfBlockIndex() {
        int expected = -1;
        assertEquals(expected, instance.getEndOfBlockIndex(), "Unexpected initial value");

        expected = 0;
        instance.endOfBlockIndex = expected;
        assertEquals(expected, instance.getEndOfBlockIndex());

        expected = 5;
        instance.endOfBlockIndex = expected;
        assertEquals(expected, instance.getEndOfBlockIndex());
    }

    /**
     * Description of test.
     *
     */
    @Test
    public void testHasCompleteEnvelopeWithRequiredEndOfData() {
        endpoint.setRequireEndOfData(true);
        assertFalse(instance.hasCompleteEnvelope(), "Unexpected initial value");

        instance.write(MllpProtocolConstants.START_OF_BLOCK);
        assertFalse(instance.hasCompleteEnvelope());

        instance.write(TEST_HL7_MESSAGE.getBytes());
        assertFalse(instance.hasCompleteEnvelope());

        instance.write(MllpProtocolConstants.END_OF_BLOCK);
        assertFalse(instance.hasCompleteEnvelope());

        instance.write(MllpProtocolConstants.END_OF_DATA);
        assertTrue(instance.hasCompleteEnvelope());

        instance.reset();
        assertFalse(instance.hasCompleteEnvelope());
    }

    /**
     * Description of test.
     *
     */
    @Test
    public void testHasCompleteEnvelopeWithOptionalEndOfData() {
        endpoint.setRequireEndOfData(false);
        assertFalse(instance.hasCompleteEnvelope(), "Unexpected initial value");

        instance.write(MllpProtocolConstants.START_OF_BLOCK);
        assertFalse(instance.hasCompleteEnvelope());

        instance.write(TEST_HL7_MESSAGE.getBytes());
        assertFalse(instance.hasCompleteEnvelope());

        instance.write(MllpProtocolConstants.END_OF_BLOCK);
        assertTrue(instance.hasCompleteEnvelope());

        instance.write(MllpProtocolConstants.END_OF_DATA);
        assertTrue(instance.hasCompleteEnvelope());

        instance.reset();
        assertFalse(instance.hasCompleteEnvelope());
    }

    /**
     * Description of test.
     *
     */
    @Test
    public void testHasStartOfBlock() {
        assertFalse(instance.hasStartOfBlock(), "Unexpected initial value");

        instance.write(MllpProtocolConstants.START_OF_BLOCK);
        assertTrue(instance.hasStartOfBlock());

        instance.reset();
        assertFalse(instance.hasStartOfBlock());

        instance.write(TEST_HL7_MESSAGE.getBytes());
        assertFalse(instance.hasStartOfBlock());

        instance.write(MllpProtocolConstants.START_OF_BLOCK);
        assertTrue(instance.hasStartOfBlock());

        instance.write(TEST_HL7_MESSAGE.getBytes());
        assertTrue(instance.hasStartOfBlock());
    }

    /**
     * Description of test.
     *
     */
    @Test
    public void testHasEndOfBlock() {
        assertFalse(instance.hasEndOfBlock(), "Unexpected initial value");

        instance.write(MllpProtocolConstants.END_OF_BLOCK);
        assertFalse(instance.hasEndOfBlock(), "START_OF_BLOCK before an END_OF_BLOCK");

        instance.reset();
        assertFalse(instance.hasEndOfBlock());

        instance.write(MllpProtocolConstants.START_OF_BLOCK);
        assertFalse(instance.hasEndOfBlock());

        instance.write(TEST_HL7_MESSAGE.getBytes());
        assertFalse(instance.hasEndOfBlock());

        instance.write(MllpProtocolConstants.END_OF_BLOCK);
        assertTrue(instance.hasEndOfBlock());

        instance.write(MllpProtocolConstants.END_OF_DATA);
        assertTrue(instance.hasEndOfBlock());

        instance.reset();
        assertFalse(instance.hasEndOfBlock());

        instance.write(MllpProtocolConstants.START_OF_BLOCK);
        assertFalse(instance.hasEndOfBlock());

        instance.write(TEST_HL7_MESSAGE.getBytes());
        assertFalse(instance.hasEndOfBlock());

        instance.write(MllpProtocolConstants.END_OF_BLOCK);
        assertTrue(instance.hasEndOfBlock());

        instance.write("BLAH".getBytes());
        assertTrue(instance.hasEndOfBlock());
    }

    /**
     * Description of test.
     *
     */
    @Test
    public void testHasEndOfData() {
        assertFalse(instance.hasEndOfData(), "Unexpected initial value");

        // Test just the END_OF_DATA
        instance.write(MllpProtocolConstants.END_OF_DATA);
        assertFalse(instance.hasEndOfData());

        instance.reset();
        assertFalse(instance.hasEndOfData());

        // Test just the terminators
        instance.write(MllpProtocolConstants.END_OF_BLOCK);
        assertFalse(instance.hasEndOfData());

        instance.write(MllpProtocolConstants.END_OF_DATA);
        assertFalse(instance.hasEndOfData(), "Need a START_OF_BLOCK before the END_OF_DATA");

        instance.reset();
        assertFalse(instance.hasEndOfData());

        instance.write(MllpProtocolConstants.START_OF_BLOCK);
        assertFalse(instance.hasEndOfData());

        instance.write(TEST_HL7_MESSAGE.getBytes());
        assertFalse(instance.hasEndOfData());

        instance.write(MllpProtocolConstants.END_OF_BLOCK);
        assertFalse(instance.hasEndOfData());

        instance.write(MllpProtocolConstants.END_OF_DATA);
        assertTrue(instance.hasEndOfData());

        instance.reset();
        assertFalse(instance.hasEndOfData());

        instance.write(MllpProtocolConstants.START_OF_BLOCK);
        assertFalse(instance.hasEndOfData());

        instance.write(TEST_HL7_MESSAGE.getBytes());
        assertFalse(instance.hasEndOfData());

        instance.write(MllpProtocolConstants.END_OF_BLOCK);
        assertFalse(instance.hasEndOfData());

        instance.write("BLAH".getBytes());
        assertFalse(instance.hasEndOfData());

        instance.write(MllpProtocolConstants.END_OF_DATA);
        assertFalse(instance.hasEndOfData());
    }

    /**
     * Description of test.
     *
     * @throws Exception in the event of a test error.
     */
    @Test
    public void testhasOutOfBandData() throws Exception {
        assertFalse(instance.hasOutOfBandData(), "Unexpected initial value");

        instance.write(buildTestBytes(true, true, true));
        assertFalse(instance.hasOutOfBandData());

        instance.write("BLAH".getBytes());
        assertTrue(instance.hasOutOfBandData());

        instance.reset();
        assertFalse(instance.hasOutOfBandData());

        instance.write("BLAH".getBytes());
        instance.write(buildTestBytes(true, true, true));
        assertTrue(instance.hasOutOfBandData());
    }

    /**
     * Description of test.
     *
     * @throws Exception in the event of a test error.
     */
    @Test
    public void testHasLeadingOutOfBandData() throws Exception {
        assertFalse(instance.hasLeadingOutOfBandData(), "Unexpected initial value");

        instance.write(buildTestBytes(true, true, true));
        assertFalse(instance.hasLeadingOutOfBandData());

        instance.write("BLAH".getBytes());
        assertFalse(instance.hasLeadingOutOfBandData());

        instance.reset();
        assertFalse(instance.hasLeadingOutOfBandData());

        instance.write("BLAH".getBytes());
        instance.write(buildTestBytes(true, true, true));
        assertTrue(instance.hasLeadingOutOfBandData());
    }

    /**
     * Description of test.
     *
     * @throws Exception in the event of a test error.
     */
    @Test
    public void testHasTrailingOutOfBandDataWithRequiredEndOfData() throws Exception {
        endpoint.setRequireEndOfData(true);

        assertFalse(instance.hasTrailingOutOfBandData(), "Unexpected initial value");

        instance.write(buildTestBytes(true, true, true));
        assertFalse(instance.hasTrailingOutOfBandData());

        instance.write("BLAH".getBytes());
        assertTrue(instance.hasTrailingOutOfBandData());

        instance.reset();
        assertFalse(instance.hasTrailingOutOfBandData());

        instance.write(buildTestBytes(true, true, false));
        assertFalse(instance.hasTrailingOutOfBandData());

        instance.write("BLAH".getBytes());
        assertFalse(instance.hasTrailingOutOfBandData());

        // Test with leading out-of-band data
        instance.reset();
        instance.write("BLAH".getBytes());
        instance.write(buildTestBytes(true, true, true));
        assertFalse(instance.hasTrailingOutOfBandData());
    }

    /**
     * Description of test.
     *
     * @throws Exception in the event of a test error.
     */
    @Test
    public void testHasTrailingOutOfBandDataWithOptionalEndOfData() throws Exception {
        endpoint.setRequireEndOfData(false);

        assertFalse(instance.hasTrailingOutOfBandData(), "Unexpected initial value");

        instance.write(buildTestBytes(true, true, true));
        assertFalse(instance.hasTrailingOutOfBandData());

        instance.write("BLAH".getBytes());
        assertTrue(instance.hasTrailingOutOfBandData());

        instance.reset();
        assertFalse(instance.hasTrailingOutOfBandData());

        instance.write(buildTestBytes(true, true, false));
        assertFalse(instance.hasTrailingOutOfBandData());

        instance.write("BLAH".getBytes());
        assertTrue(instance.hasTrailingOutOfBandData());

        // Test with leading out-of-band data
        instance.reset();
        instance.write("BLAH".getBytes());
        instance.write(buildTestBytes(true, true, true));
        assertFalse(instance.hasTrailingOutOfBandData());
    }

    /**
     * Description of test.
     *
     * @throws Exception in the event of a test error.
     */
    @Test
    public void testGetLeadingOutOfBandData() throws Exception {
        assertNull(instance.getLeadingOutOfBandData(), "Unexpected initial value");

        instance.write(buildTestBytes(true, true, true));
        assertNull(instance.getLeadingOutOfBandData());

        instance.write("BLAH".getBytes());
        assertNull(instance.getLeadingOutOfBandData());

        instance.reset();
        assertNull(instance.getLeadingOutOfBandData());

        byte[] expected = "BLAH".getBytes();
        instance.write(expected);
        instance.write(buildTestBytes(true, true, true));
        assertArrayEquals(expected, instance.getLeadingOutOfBandData());
    }

    /**
     * Description of test.
     *
     * @throws Exception in the event of a test error.
     */
    @Test
    public void testGetTrailingOutOfBandDataWithRequiredEndOfData() throws Exception {
        endpoint.setRequireEndOfData(true);

        assertNull(instance.getTrailingOutOfBandData(), "Unexpected initial value");

        // Test with END_OF_DATA
        instance.write(buildTestBytes(true, true, true));
        assertNull(instance.getTrailingOutOfBandData());

        byte[] expected = "BLAH".getBytes();
        instance.write(expected);
        assertArrayEquals(expected, instance.getTrailingOutOfBandData());

        instance.reset();
        assertNull(instance.getTrailingOutOfBandData());

        // Test without END_OF_DATA
        instance.write(buildTestBytes(true, true, false));
        instance.write(expected);
        assertNull(instance.getTrailingOutOfBandData());

        // Test without END_OF_BLOCK
        instance.reset();
        instance.write(expected);
        assertNull(instance.getTrailingOutOfBandData());

        // Test without envelope termination
        instance.reset();
        instance.write(buildTestBytes(true, false, false));
        assertNull(instance.getTrailingOutOfBandData());

        instance.reset();

        // Test with END_OF_DATA
        instance.write(expected);
        instance.write(buildTestBytes(true, true, true));
        assertNull(instance.getTrailingOutOfBandData());

        // Test without END_OF_DATA
        instance.reset();
        instance.write(buildTestBytes(true, true, true));
        assertNull(instance.getTrailingOutOfBandData());
    }

    /**
     * Description of test.
     *
     * @throws Exception in the event of a test error.
     */
    @Test
    public void testGetTrailingOutOfBandDataWithOptionalEndOfData() throws Exception {
        endpoint.setRequireEndOfData(false);

        assertNull(instance.getTrailingOutOfBandData(), "Unexpected initial value");

        // Test with END_OF_DATA
        instance.write(buildTestBytes(true, true, true));
        assertNull(instance.getTrailingOutOfBandData());

        byte[] expected = "BLAH".getBytes();
        instance.write(expected);
        assertArrayEquals(expected, instance.getTrailingOutOfBandData());

        instance.reset();
        assertNull(instance.getTrailingOutOfBandData());

        // Test without END_OF_DATA
        instance.write(buildTestBytes(true, true, false));
        instance.write(expected);
        assertArrayEquals(expected, instance.getTrailingOutOfBandData());

        // Test without END_OF_BLOCK
        instance.reset();
        instance.write(buildTestBytes(true, false, true));
        assertNull(instance.getTrailingOutOfBandData());

        // Test without envelope termination
        instance.reset();
        instance.write(buildTestBytes(true, false, false));
        assertNull(instance.getTrailingOutOfBandData());

        instance.reset();

        // Test with END_OF_DATA
        instance.write(expected);
        instance.write(buildTestBytes(true, true, true));
        assertNull(instance.getTrailingOutOfBandData());

        // Test without END_OF_DATA
        instance.reset();
        instance.write(buildTestBytes(true, true, true));
        assertNull(instance.getTrailingOutOfBandData());
    }

}
