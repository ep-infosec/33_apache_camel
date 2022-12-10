
/*
 * Camel ApiCollection generated by camel-api-component-maven-plugin
 */
package org.apache.camel.component.google.calendar.internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.camel.component.google.calendar.GoogleCalendarConfiguration;
import org.apache.camel.component.google.calendar.CalendarAclEndpointConfiguration;
import org.apache.camel.component.google.calendar.CalendarCalendarListEndpointConfiguration;
import org.apache.camel.component.google.calendar.CalendarCalendarsEndpointConfiguration;
import org.apache.camel.component.google.calendar.CalendarChannelsEndpointConfiguration;
import org.apache.camel.component.google.calendar.CalendarColorsEndpointConfiguration;
import org.apache.camel.component.google.calendar.CalendarFreebusyEndpointConfiguration;
import org.apache.camel.component.google.calendar.CalendarEventsEndpointConfiguration;
import org.apache.camel.component.google.calendar.CalendarSettingsEndpointConfiguration;

import org.apache.camel.support.component.ApiCollection;
import org.apache.camel.support.component.ApiMethod;
import org.apache.camel.support.component.ApiMethodHelper;

/**
 * Camel {@link ApiCollection} for GoogleCalendar
 */
public final class GoogleCalendarApiCollection extends ApiCollection<GoogleCalendarApiName, GoogleCalendarConfiguration> {

    private static GoogleCalendarApiCollection collection;

    private GoogleCalendarApiCollection() {
        final Map<String, String> aliases = new HashMap<String, String>();
        final Map<GoogleCalendarApiName, ApiMethodHelper<? extends ApiMethod>> apiHelpers = new HashMap<>();
        final Map<Class<? extends ApiMethod>, GoogleCalendarApiName> apiMethods = new HashMap<>();

        List<String> nullableArgs;

        aliases.clear();
        nullableArgs = Arrays.asList();
        apiHelpers.put(GoogleCalendarApiName.ACL, new ApiMethodHelper<CalendarAclApiMethod>(CalendarAclApiMethod.class, aliases, nullableArgs));
        apiMethods.put(CalendarAclApiMethod.class, GoogleCalendarApiName.ACL);

        aliases.clear();
        nullableArgs = Arrays.asList();
        apiHelpers.put(GoogleCalendarApiName.LIST, new ApiMethodHelper<CalendarCalendarListApiMethod>(CalendarCalendarListApiMethod.class, aliases, nullableArgs));
        apiMethods.put(CalendarCalendarListApiMethod.class, GoogleCalendarApiName.LIST);

        aliases.clear();
        nullableArgs = Arrays.asList();
        apiHelpers.put(GoogleCalendarApiName.CALENDARS, new ApiMethodHelper<CalendarCalendarsApiMethod>(CalendarCalendarsApiMethod.class, aliases, nullableArgs));
        apiMethods.put(CalendarCalendarsApiMethod.class, GoogleCalendarApiName.CALENDARS);

        aliases.clear();
        nullableArgs = Arrays.asList();
        apiHelpers.put(GoogleCalendarApiName.CHANNELS, new ApiMethodHelper<CalendarChannelsApiMethod>(CalendarChannelsApiMethod.class, aliases, nullableArgs));
        apiMethods.put(CalendarChannelsApiMethod.class, GoogleCalendarApiName.CHANNELS);

        aliases.clear();
        nullableArgs = Arrays.asList();
        apiHelpers.put(GoogleCalendarApiName.COLORS, new ApiMethodHelper<CalendarColorsApiMethod>(CalendarColorsApiMethod.class, aliases, nullableArgs));
        apiMethods.put(CalendarColorsApiMethod.class, GoogleCalendarApiName.COLORS);

        aliases.clear();
        nullableArgs = Arrays.asList();
        apiHelpers.put(GoogleCalendarApiName.FREEBUSY, new ApiMethodHelper<CalendarFreebusyApiMethod>(CalendarFreebusyApiMethod.class, aliases, nullableArgs));
        apiMethods.put(CalendarFreebusyApiMethod.class, GoogleCalendarApiName.FREEBUSY);

        aliases.clear();
        nullableArgs = Arrays.asList();
        apiHelpers.put(GoogleCalendarApiName.EVENTS, new ApiMethodHelper<CalendarEventsApiMethod>(CalendarEventsApiMethod.class, aliases, nullableArgs));
        apiMethods.put(CalendarEventsApiMethod.class, GoogleCalendarApiName.EVENTS);

        aliases.clear();
        nullableArgs = Arrays.asList();
        apiHelpers.put(GoogleCalendarApiName.SETTINGS, new ApiMethodHelper<CalendarSettingsApiMethod>(CalendarSettingsApiMethod.class, aliases, nullableArgs));
        apiMethods.put(CalendarSettingsApiMethod.class, GoogleCalendarApiName.SETTINGS);

        setApiHelpers(apiHelpers);
        setApiMethods(apiMethods);
    }

    public GoogleCalendarConfiguration getEndpointConfiguration(GoogleCalendarApiName apiName) {
        GoogleCalendarConfiguration result = null;
        switch (apiName) {
            case ACL:
                result = new CalendarAclEndpointConfiguration();
                break;
            case LIST:
                result = new CalendarCalendarListEndpointConfiguration();
                break;
            case CALENDARS:
                result = new CalendarCalendarsEndpointConfiguration();
                break;
            case CHANNELS:
                result = new CalendarChannelsEndpointConfiguration();
                break;
            case COLORS:
                result = new CalendarColorsEndpointConfiguration();
                break;
            case FREEBUSY:
                result = new CalendarFreebusyEndpointConfiguration();
                break;
            case EVENTS:
                result = new CalendarEventsEndpointConfiguration();
                break;
            case SETTINGS:
                result = new CalendarSettingsEndpointConfiguration();
                break;
        }
        return result;
    }

    public static synchronized GoogleCalendarApiCollection getCollection() {
        if (collection == null) {
            collection = new GoogleCalendarApiCollection();
        }
        return collection;
    }
}
