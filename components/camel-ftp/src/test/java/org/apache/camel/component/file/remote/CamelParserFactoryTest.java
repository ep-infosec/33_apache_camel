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
package org.apache.camel.component.file.remote;

import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFileEntryParser;
import org.apache.commons.net.ftp.parser.CompositeFileEntryParser;
import org.apache.commons.net.ftp.parser.MVSFTPEntryParser;
import org.apache.commons.net.ftp.parser.MacOsPeterFTPEntryParser;
import org.apache.commons.net.ftp.parser.NTFTPEntryParser;
import org.apache.commons.net.ftp.parser.NetwareFTPEntryParser;
import org.apache.commons.net.ftp.parser.OS2FTPEntryParser;
import org.apache.commons.net.ftp.parser.OS400FTPEntryParser;
import org.apache.commons.net.ftp.parser.UnixFTPEntryParser;
import org.apache.commons.net.ftp.parser.VMSVersioningFTPEntryParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CamelParserFactoryTest {

    private static final CamelFTPParserFactory CAMEL_PARSER_FACTORY = new CamelFTPParserFactory(null);

    @Mock
    private FTPClientConfig ftpClientConfig;

    @BeforeEach
    public void setup() {
        when(ftpClientConfig.getDefaultDateFormatStr()).thenReturn("yyyy-MM-dd");
    }

    @Test
    public void createFileEntryParserUnix() {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("bla unix bla");
        FTPFileEntryParser result = CAMEL_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(UnixFTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserLinux() {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("bla linux bla");
        FTPFileEntryParser result = CAMEL_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(UnixFTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserTypeL8() {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("bla type: l8 bla");
        FTPFileEntryParser result = CAMEL_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(UnixFTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserVms() {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("bla vms bla");
        FTPFileEntryParser result = CAMEL_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(VMSVersioningFTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserPlainWindows() {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("WINDOWS");
        FTPFileEntryParser result = CAMEL_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(NTFTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserNotPlainWindows() {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("WINDOWS XP");
        FTPFileEntryParser result = CAMEL_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(CompositeFileEntryParser.class));
    }

    @Test
    public void createFileEntryParserWin32() {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("bla WIN32 bla");
        FTPFileEntryParser result = CAMEL_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(CompositeFileEntryParser.class));
    }

    @Test
    public void createFileEntryParserOs2() {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("bla OS/2 bla");
        FTPFileEntryParser result = CAMEL_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(OS2FTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserPlainOs400() {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("OS/400");
        FTPFileEntryParser result = CAMEL_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(OS400FTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserNotPlainOs400() {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("OS/400 bla");
        FTPFileEntryParser result = CAMEL_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(CompositeFileEntryParser.class));
    }

    @Test
    public void createFileEntryParserMvs() {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("bla MvS bla");
        FTPFileEntryParser result = CAMEL_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(MVSFTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserNetware() {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("bla NeTwArE bla");
        FTPFileEntryParser result = CAMEL_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(NetwareFTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserMacOsPeter() {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("bla MaCoS PeTER bla");
        FTPFileEntryParser result = CAMEL_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(MacOsPeterFTPEntryParser.class));
    }

}
