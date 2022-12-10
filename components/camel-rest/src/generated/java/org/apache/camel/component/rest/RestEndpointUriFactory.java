/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.rest;

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
public class RestEndpointUriFactory extends org.apache.camel.support.component.EndpointUriFactorySupport implements EndpointUriFactory {

    private static final String BASE = ":method:path:uriTemplate";

    private static final Set<String> PROPERTY_NAMES;
    private static final Set<String> SECRET_PROPERTY_NAMES;
    private static final Set<String> MULTI_VALUE_PREFIXES;
    static {
        Set<String> props = new HashSet<>(19);
        props.add("apiDoc");
        props.add("bindingMode");
        props.add("bridgeErrorHandler");
        props.add("consumerComponentName");
        props.add("consumes");
        props.add("description");
        props.add("exceptionHandler");
        props.add("exchangePattern");
        props.add("host");
        props.add("inType");
        props.add("lazyStartProducer");
        props.add("method");
        props.add("outType");
        props.add("path");
        props.add("producerComponentName");
        props.add("produces");
        props.add("queryParameters");
        props.add("routeId");
        props.add("uriTemplate");
        PROPERTY_NAMES = Collections.unmodifiableSet(props);
        SECRET_PROPERTY_NAMES = Collections.emptySet();
        MULTI_VALUE_PREFIXES = Collections.emptySet();
    }

    @Override
    public boolean isEnabled(String scheme) {
        return "rest".equals(scheme);
    }

    @Override
    public String buildUri(String scheme, Map<String, Object> properties, boolean encode) throws URISyntaxException {
        String syntax = scheme + BASE;
        String uri = syntax;

        Map<String, Object> copy = new HashMap<>(properties);

        uri = buildPathParameter(syntax, uri, "method", null, true, copy);
        uri = buildPathParameter(syntax, uri, "path", null, true, copy);
        uri = buildPathParameter(syntax, uri, "uriTemplate", null, false, copy);
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
        return true;
    }
}

