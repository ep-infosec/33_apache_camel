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
package org.apache.camel.component.corda;

import org.apache.camel.spi.Metadata;

public interface CordaConstants {
    @Metadata(label = "producer", description = "The operation to perform", javaType = "String")
    String OPERATION = "OPERATION";
    String NODE_INFO = "NODE_INFO";
    String CURRENT_NODE_TIME = "CURRENT_NODE_TIME";
    String GET_PROTOCOL_VERSION = "GET_PROTOCOL_VERSION";
    String NETWORK_MAP_SNAPSHOT = "NETWORK_MAP_SNAPSHOT";
    String REGISTERED_FLOWS = "REGISTERED_FLOWS";
    String CLEAR_NETWORK_MAP_CACHE = "CLEAR_NETWORK_MAP_CACHE";
    String IS_FLOWS_DRAINING_MODE_ENABLED = "IS_FLOWS_DRAINING_MODE_ENABLED";
    String ADD_VAULT_TRANSACTION_NOTE = "ADD_VAULT_TRANSACTION_NOTE";
    String NOTARY_IDENTITIES = "NOTARY_IDENTITIES";
    String SET_FLOWS_DRAINING_MODE_ENABLED = "SET_FLOWS_DRAINING_MODE_ENABLED";
    String GET_VAULT_TRANSACTION_NOTES = "GET_VAULT_TRANSACTION_NOTES";
    String UPLOAD_ATTACHMENT = "UPLOAD_ATTACHMENT";
    String ATTACHMENT_EXISTS = "ATTACHMENT_EXISTS";
    String OPEN_ATTACHMENT = "OPEN_ATTACHMENT";
    String QUERY_ATTACHMENTS = "QUERY_ATTACHMENTS";
    String NODE_INFO_FROM_PARTY = "NODE_INFO_FROM_PARTY";
    String NOTARY_PARTY_FROM_X500_NAME = "NOTARY_PARTY_FROM_X500_NAME";
    String PARTIES_FROM_NAME = "PARTIES_FROM_NAME";
    String PARTIES_FROM_KEY = "PARTIES_FROM_KEY";
    String START_FLOW_DYNAMIC = "START_FLOW_DYNAMIC";
    String STATE_MACHINE_SNAPSHOT = "STATE_MACHINE_SNAPSHOT";
    String STATE_MACHINE_RECORDED_TRANSACTION_MAPPING_SNAPSHOT = "STATE_MACHINE_RECORDED_TRANSACTION_MAPPING_SNAPSHOT";
    String WELL_KNOWN_PARTY_FROM_X500_NAME = "WELL_KNOWN_PARTY_FROM_X500_NAME";
    String WELL_KNOWN_PARTY_FROM_ANONYMOUS = "WELL_KNOWN_PARTY_FROM_ANONYMOUS";
    String VAULT_QUERY = "VAULT_QUERY";
    String VAULT_QUERY_BY = "VAULT_QUERY_BY";
    String VAULT_QUERY_BY_CRITERIA = "VAULT_QUERY_BY_CRITERIA";
    String VAULT_QUERY_BY_WITH_PAGING_SPEC = "VAULT_QUERY_BY_WITH_PAGING_SPEC";
    String VAULT_QUERY_BY_WITH_SORTING = "VAULT_QUERY_BY_WITH_SORTING";

    String VAULT_TRACK = "VAULT_TRACK";
    String VAULT_TRACK_BY = "VAULT_TRACK_BY";
    String VAULT_TRACK_BY_CRITERIA = "VAULT_TRACK_BY_CRITERIA";
    String VAULT_TRACK_BY_WITH_PAGING_SPEC = "VAULT_TRACK_BY_WITH_PAGING_SPEC";
    String VAULT_TRACK_BY_WITH_SORTING = "VAULT_TRACK_BY_WITH_SORTING";
    String STATE_MACHINE_FEED = "STATE_MACHINE_FEED";
    String NETWORK_MAP_FEED = "NETWORK_MAP_FEED";
    String STATE_MACHINE_RECORDED_TRANSACTION_MAPPING_FEED = "STATE_MACHINE_RECORDED_TRANSACTION_MAPPING_FEED";
    String START_TRACKED_FLOW_DYNAMIC = "START_TRACKED_FLOW_DYNAMIC";

    @Metadata(label = "producer", description = "The attachment query criteria",
              javaType = "net.corda.core.node.services.vault.AttachmentQueryCriteria")
    String ATTACHMENT_QUERY_CRITERIA = "ATTACHMENT_QUERY_CRITERIA";
    @Metadata(label = "producer", description = "The sort")
    String SORT = "SORT";
    @Metadata(label = "producer",
              description = "If true, a case sensitive match is done against each component of each X.500 name.",
              javaType = "Boolean")
    String EXACT_MATCH = "EXACT_MATCH";
    @Metadata(label = "producer", description = "The arguments.", javaType = "Object[]")
    String ARGUMENTS = "ARGUMENTS";
    @Metadata(label = "producer", description = "The value of the node's flows draining mode.", javaType = "Boolean")
    String DRAINING_MODE = "DRAINING_MODE";
    @Metadata(label = "producer", description = "Container for a cryptographically secure hash value.",
              javaType = "net.corda.core.crypto.SecureHash")
    String SECURE_HASH = "SECURE_HASH";
    @Metadata(label = "producer", description = "The query criteria.",
              javaType = "net.corda.core.node.services.vault.QueryCriteria")
    String QUERY_CRITERIA = "QUERY_CRITERIA";
    @Metadata(label = "producer", description = "The PageSpecification allows specification of a page number and page size",
              javaType = "net.corda.core.node.services.vault.PageSpecification")
    String PAGE_SPECIFICATION = "PAGE_SPECIFICATION";

    String TERMINATE = "TERMINATE";
    String ACCEPT_NEWNETWORK_PARAMETERS = "ACCEPT_NEWNETWORK_PARAMETERS";
    String IS_WAITING_FOR_SHUTDOWN = "IS_WAITING_FOR_SHUTDOWN";
    String KILL_FLOW = "KILL_FLOW";
    String NETWORK_PARAMETERS_FEED = "NETWORK_PARAMETERS_FEED";
    String REFRESH_NETWORK_MAP_CACHE = "REFRESH_NETWORK_MAP_CACHE";
    String SHUTDOWN = "SHUTDOWN";
    String UPLOAD_ATTACHMENT_WITH_META_DATA = "UPLOAD_ATTACHMENT_WITH_META_DATA";
    String WAIT_UNTIL_NETWORK_READY = "WAIT_UNTIL_NETWORK_READY";

}
