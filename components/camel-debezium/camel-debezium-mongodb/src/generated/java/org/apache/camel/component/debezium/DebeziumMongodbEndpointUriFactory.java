/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.debezium;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.camel.spi.EndpointUriFactory;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
public class DebeziumMongodbEndpointUriFactory extends org.apache.camel.support.component.EndpointUriFactorySupport implements EndpointUriFactory {

    private static final String BASE = ":name";

    private static final Set<String> PROPERTY_NAMES;
    private static final Set<String> SECRET_PROPERTY_NAMES;
    private static final Set<String> MULTI_VALUE_PREFIXES;
    static {
        Set<String> props = new HashSet<>(63);
        props.add("additionalProperties");
        props.add("bridgeErrorHandler");
        props.add("captureMode");
        props.add("collectionExcludeList");
        props.add("collectionIncludeList");
        props.add("connectBackoffInitialDelayMs");
        props.add("connectBackoffMaxDelayMs");
        props.add("connectMaxAttempts");
        props.add("converters");
        props.add("cursorMaxAwaitTimeMs");
        props.add("databaseExcludeList");
        props.add("databaseHistoryFileFilename");
        props.add("databaseIncludeList");
        props.add("eventProcessingFailureHandlingMode");
        props.add("exceptionHandler");
        props.add("exchangePattern");
        props.add("fieldExcludeList");
        props.add("fieldRenames");
        props.add("heartbeatIntervalMs");
        props.add("heartbeatTopicsPrefix");
        props.add("internalKeyConverter");
        props.add("internalValueConverter");
        props.add("maxBatchSize");
        props.add("maxQueueSize");
        props.add("maxQueueSizeInBytes");
        props.add("mongodbAuthsource");
        props.add("mongodbConnectTimeoutMs");
        props.add("mongodbHosts");
        props.add("mongodbMembersAutoDiscover");
        props.add("mongodbName");
        props.add("mongodbPassword");
        props.add("mongodbPollIntervalMs");
        props.add("mongodbServerSelectionTimeoutMs");
        props.add("mongodbSocketTimeoutMs");
        props.add("mongodbSslEnabled");
        props.add("mongodbSslInvalidHostnameAllowed");
        props.add("mongodbUser");
        props.add("name");
        props.add("offsetCommitPolicy");
        props.add("offsetCommitTimeoutMs");
        props.add("offsetFlushIntervalMs");
        props.add("offsetStorage");
        props.add("offsetStorageFileName");
        props.add("offsetStoragePartitions");
        props.add("offsetStorageReplicationFactor");
        props.add("offsetStorageTopic");
        props.add("pollIntervalMs");
        props.add("provideTransactionMetadata");
        props.add("queryFetchSize");
        props.add("retriableRestartConnectorWaitMs");
        props.add("sanitizeFieldNames");
        props.add("schemaNameAdjustmentMode");
        props.add("signalDataCollection");
        props.add("skippedOperations");
        props.add("snapshotCollectionFilterOverrides");
        props.add("snapshotDelayMs");
        props.add("snapshotFetchSize");
        props.add("snapshotIncludeCollectionList");
        props.add("snapshotMaxThreads");
        props.add("snapshotMode");
        props.add("sourceStructVersion");
        props.add("tombstonesOnDelete");
        props.add("transactionTopic");
        PROPERTY_NAMES = Collections.unmodifiableSet(props);
        SECRET_PROPERTY_NAMES = Collections.emptySet();
        Set<String> prefixes = new HashSet<>(1);
        prefixes.add("additionalProperties.");
        MULTI_VALUE_PREFIXES = Collections.unmodifiableSet(prefixes);
    }

    @Override
    public boolean isEnabled(String scheme) {
        return "debezium-mongodb".equals(scheme);
    }

    @Override
    public String buildUri(String scheme, Map<String, Object> properties, boolean encode) throws URISyntaxException {
        String syntax = scheme + BASE;
        String uri = syntax;

        Map<String, Object> copy = new HashMap<>(properties);

        uri = buildPathParameter(syntax, uri, "name", null, true, copy);
        uri = buildQueryParameters(uri, copy, encode);
        return uri;
    }

    @Override
    public Set<String> propertyNames() {
        return PROPERTY_NAMES;
    }

    @Override
    public Set<String> secretPropertyNames() {
        return SECRET_PROPERTY_NAMES;
    }

    @Override
    public Set<String> multiValuePrefixes() {
        return MULTI_VALUE_PREFIXES;
    }

    @Override
    public boolean isLenientProperties() {
        return false;
    }
}
