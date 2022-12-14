/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.facebook;

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
public class FacebookEndpointUriFactory extends org.apache.camel.support.component.EndpointUriFactorySupport implements EndpointUriFactory {

    private static final String BASE = ":methodName";

    private static final Set<String> PROPERTY_NAMES;
    private static final Set<String> SECRET_PROPERTY_NAMES;
    private static final Set<String> MULTI_VALUE_PREFIXES;
    static {
        Set<String> props = new HashSet<>(103);
        props.add("achievementURL");
        props.add("albumId");
        props.add("albumUpdate");
        props.add("appId");
        props.add("bridgeErrorHandler");
        props.add("center");
        props.add("checkinId");
        props.add("checkinUpdate");
        props.add("clientURL");
        props.add("clientVersion");
        props.add("commentId");
        props.add("commentUpdate");
        props.add("debugEnabled");
        props.add("description");
        props.add("distance");
        props.add("domainId");
        props.add("domainName");
        props.add("domainNames");
        props.add("eventId");
        props.add("eventUpdate");
        props.add("exceptionHandler");
        props.add("exchangePattern");
        props.add("friendId");
        props.add("friendUserId");
        props.add("friendlistId");
        props.add("friendlistName");
        props.add("groupId");
        props.add("gzipEnabled");
        props.add("httpConnectionTimeout");
        props.add("httpDefaultMaxPerRoute");
        props.add("httpMaxTotalConnections");
        props.add("httpProxyHost");
        props.add("httpProxyPassword");
        props.add("httpProxyPort");
        props.add("httpProxyUser");
        props.add("httpReadTimeout");
        props.add("httpRetryCount");
        props.add("httpRetryIntervalSeconds");
        props.add("httpStreamingReadTimeout");
        props.add("ids");
        props.add("inBody");
        props.add("includeRead");
        props.add("isHidden");
        props.add("jsonStoreEnabled");
        props.add("lazyStartProducer");
        props.add("link");
        props.add("linkId");
        props.add("locale");
        props.add("mbeanEnabled");
        props.add("message");
        props.add("messageId");
        props.add("methodName");
        props.add("metric");
        props.add("milestoneId");
        props.add("name");
        props.add("noteId");
        props.add("notificationId");
        props.add("oAuthAccessToken");
        props.add("oAuthAccessTokenURL");
        props.add("oAuthAppId");
        props.add("oAuthAppSecret");
        props.add("oAuthAuthorizationURL");
        props.add("oAuthPermissions");
        props.add("objectId");
        props.add("offerId");
        props.add("optionDescription");
        props.add("pageId");
        props.add("permissionName");
        props.add("permissions");
        props.add("photoId");
        props.add("pictureId");
        props.add("pictureId2");
        props.add("pictureSize");
        props.add("placeId");
        props.add("postId");
        props.add("postUpdate");
        props.add("prettyDebugEnabled");
        props.add("queries");
        props.add("query");
        props.add("questionId");
        props.add("reading");
        props.add("readingOptions");
        props.add("restBaseURL");
        props.add("scoreValue");
        props.add("size");
        props.add("source");
        props.add("subject");
        props.add("tabId");
        props.add("tagUpdate");
        props.add("testUser1");
        props.add("testUser2");
        props.add("testUserId");
        props.add("title");
        props.add("toUserId");
        props.add("toUserIds");
        props.add("useSSL");
        props.add("userId");
        props.add("userId1");
        props.add("userId2");
        props.add("userIds");
        props.add("userLocale");
        props.add("videoBaseURL");
        props.add("videoId");
        PROPERTY_NAMES = Collections.unmodifiableSet(props);
        Set<String> secretProps = new HashSet<>(3);
        secretProps.add("oAuthAccessToken");
        secretProps.add("oAuthAppId");
        secretProps.add("oAuthAppSecret");
        SECRET_PROPERTY_NAMES = Collections.unmodifiableSet(secretProps);
        Set<String> prefixes = new HashSet<>(1);
        prefixes.add("reading.");
        MULTI_VALUE_PREFIXES = Collections.unmodifiableSet(prefixes);
    }

    @Override
    public boolean isEnabled(String scheme) {
        return "facebook".equals(scheme);
    }

    @Override
    public String buildUri(String scheme, Map<String, Object> properties, boolean encode) throws URISyntaxException {
        String syntax = scheme + BASE;
        String uri = syntax;

        Map<String, Object> copy = new HashMap<>(properties);

        uri = buildPathParameter(syntax, uri, "methodName", null, true, copy);
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

