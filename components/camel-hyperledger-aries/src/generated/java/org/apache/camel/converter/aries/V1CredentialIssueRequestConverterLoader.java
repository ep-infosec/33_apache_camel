/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.converter.aries;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.DeferredContextBinding;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConversionException;
import org.apache.camel.TypeConverterLoaderException;
import org.apache.camel.spi.TypeConverterLoader;
import org.apache.camel.spi.TypeConverterRegistry;
import org.apache.camel.support.SimpleTypeConverter;
import org.apache.camel.support.TypeConverterSupport;
import org.apache.camel.util.DoubleMap;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@SuppressWarnings("unchecked")
@DeferredContextBinding
public final class V1CredentialIssueRequestConverterLoader implements TypeConverterLoader, CamelContextAware {

    private CamelContext camelContext;

    public V1CredentialIssueRequestConverterLoader() {
    }

    @Override
    public void setCamelContext(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    public CamelContext getCamelContext() {
        return camelContext;
    }

    @Override
    public void load(TypeConverterRegistry registry) throws TypeConverterLoaderException {
        registerConverters(registry);
    }

    private void registerConverters(TypeConverterRegistry registry) {
        addTypeConverter(registry, org.hyperledger.aries.api.issue_credential_v1.V1CredentialIssueRequest.class, com.google.gson.JsonObject.class, false,
            (type, exchange, value) -> org.apache.camel.converter.aries.V1CredentialIssueRequestConverter.toAries((com.google.gson.JsonObject) value));
        addTypeConverter(registry, org.hyperledger.aries.api.issue_credential_v1.V1CredentialIssueRequest.class, java.lang.String.class, false,
            (type, exchange, value) -> org.apache.camel.converter.aries.V1CredentialIssueRequestConverter.toAries((java.lang.String) value));
        addTypeConverter(registry, org.hyperledger.aries.api.issue_credential_v1.V1CredentialIssueRequest.class, java.util.Map.class, false,
            (type, exchange, value) -> org.apache.camel.converter.aries.V1CredentialIssueRequestConverter.toAries((java.util.Map) value));
    }

    private static void addTypeConverter(TypeConverterRegistry registry, Class<?> toType, Class<?> fromType, boolean allowNull, SimpleTypeConverter.ConversionMethod method) { 
        registry.addTypeConverter(toType, fromType, new SimpleTypeConverter(allowNull, method));
    }

}
