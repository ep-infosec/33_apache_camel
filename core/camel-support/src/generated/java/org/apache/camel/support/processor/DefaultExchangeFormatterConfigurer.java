/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.support.processor;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.ExtendedPropertyConfigurerGetter;
import org.apache.camel.spi.PropertyConfigurerGetter;
import org.apache.camel.spi.ConfigurerStrategy;
import org.apache.camel.spi.GeneratedPropertyConfigurer;
import org.apache.camel.util.CaseInsensitiveMap;
import org.apache.camel.support.processor.DefaultExchangeFormatter;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@SuppressWarnings("unchecked")
public class DefaultExchangeFormatterConfigurer extends org.apache.camel.support.component.PropertyConfigurerSupport implements GeneratedPropertyConfigurer, PropertyConfigurerGetter {

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        org.apache.camel.support.processor.DefaultExchangeFormatter target = (org.apache.camel.support.processor.DefaultExchangeFormatter) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "maxchars":
        case "MaxChars": target.setMaxChars(property(camelContext, int.class, value)); return true;
        case "multiline":
        case "Multiline": target.setMultiline(property(camelContext, boolean.class, value)); return true;
        case "plain":
        case "Plain": target.setPlain(property(camelContext, boolean.class, value)); return true;
        case "showall":
        case "ShowAll": target.setShowAll(property(camelContext, boolean.class, value)); return true;
        case "showallproperties":
        case "ShowAllProperties": target.setShowAllProperties(property(camelContext, boolean.class, value)); return true;
        case "showbody":
        case "ShowBody": target.setShowBody(property(camelContext, boolean.class, value)); return true;
        case "showbodytype":
        case "ShowBodyType": target.setShowBodyType(property(camelContext, boolean.class, value)); return true;
        case "showcachedstreams":
        case "ShowCachedStreams": target.setShowCachedStreams(property(camelContext, boolean.class, value)); return true;
        case "showcaughtexception":
        case "ShowCaughtException": target.setShowCaughtException(property(camelContext, boolean.class, value)); return true;
        case "showexception":
        case "ShowException": target.setShowException(property(camelContext, boolean.class, value)); return true;
        case "showexchangeid":
        case "ShowExchangeId": target.setShowExchangeId(property(camelContext, boolean.class, value)); return true;
        case "showexchangepattern":
        case "ShowExchangePattern": target.setShowExchangePattern(property(camelContext, boolean.class, value)); return true;
        case "showfiles":
        case "ShowFiles": target.setShowFiles(property(camelContext, boolean.class, value)); return true;
        case "showfuture":
        case "ShowFuture": target.setShowFuture(property(camelContext, boolean.class, value)); return true;
        case "showheaders":
        case "ShowHeaders": target.setShowHeaders(property(camelContext, boolean.class, value)); return true;
        case "showproperties":
        case "ShowProperties": target.setShowProperties(property(camelContext, boolean.class, value)); return true;
        case "showstacktrace":
        case "ShowStackTrace": target.setShowStackTrace(property(camelContext, boolean.class, value)); return true;
        case "showstreams":
        case "ShowStreams": target.setShowStreams(property(camelContext, boolean.class, value)); return true;
        case "skipbodylineseparator":
        case "SkipBodyLineSeparator": target.setSkipBodyLineSeparator(property(camelContext, boolean.class, value)); return true;
        case "style":
        case "Style": target.setStyle(property(camelContext, org.apache.camel.support.processor.DefaultExchangeFormatter.OutputStyle.class, value)); return true;
        default: return false;
        }
    }

    @Override
    public Class<?> getOptionType(String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "maxchars":
        case "MaxChars": return int.class;
        case "multiline":
        case "Multiline": return boolean.class;
        case "plain":
        case "Plain": return boolean.class;
        case "showall":
        case "ShowAll": return boolean.class;
        case "showallproperties":
        case "ShowAllProperties": return boolean.class;
        case "showbody":
        case "ShowBody": return boolean.class;
        case "showbodytype":
        case "ShowBodyType": return boolean.class;
        case "showcachedstreams":
        case "ShowCachedStreams": return boolean.class;
        case "showcaughtexception":
        case "ShowCaughtException": return boolean.class;
        case "showexception":
        case "ShowException": return boolean.class;
        case "showexchangeid":
        case "ShowExchangeId": return boolean.class;
        case "showexchangepattern":
        case "ShowExchangePattern": return boolean.class;
        case "showfiles":
        case "ShowFiles": return boolean.class;
        case "showfuture":
        case "ShowFuture": return boolean.class;
        case "showheaders":
        case "ShowHeaders": return boolean.class;
        case "showproperties":
        case "ShowProperties": return boolean.class;
        case "showstacktrace":
        case "ShowStackTrace": return boolean.class;
        case "showstreams":
        case "ShowStreams": return boolean.class;
        case "skipbodylineseparator":
        case "SkipBodyLineSeparator": return boolean.class;
        case "style":
        case "Style": return org.apache.camel.support.processor.DefaultExchangeFormatter.OutputStyle.class;
        default: return null;
        }
    }

    @Override
    public Object getOptionValue(Object obj, String name, boolean ignoreCase) {
        org.apache.camel.support.processor.DefaultExchangeFormatter target = (org.apache.camel.support.processor.DefaultExchangeFormatter) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "maxchars":
        case "MaxChars": return target.getMaxChars();
        case "multiline":
        case "Multiline": return target.isMultiline();
        case "plain":
        case "Plain": return target.isPlain();
        case "showall":
        case "ShowAll": return target.isShowAll();
        case "showallproperties":
        case "ShowAllProperties": return target.isShowAllProperties();
        case "showbody":
        case "ShowBody": return target.isShowBody();
        case "showbodytype":
        case "ShowBodyType": return target.isShowBodyType();
        case "showcachedstreams":
        case "ShowCachedStreams": return target.isShowCachedStreams();
        case "showcaughtexception":
        case "ShowCaughtException": return target.isShowCaughtException();
        case "showexception":
        case "ShowException": return target.isShowException();
        case "showexchangeid":
        case "ShowExchangeId": return target.isShowExchangeId();
        case "showexchangepattern":
        case "ShowExchangePattern": return target.isShowExchangePattern();
        case "showfiles":
        case "ShowFiles": return target.isShowFiles();
        case "showfuture":
        case "ShowFuture": return target.isShowFuture();
        case "showheaders":
        case "ShowHeaders": return target.isShowHeaders();
        case "showproperties":
        case "ShowProperties": return target.isShowProperties();
        case "showstacktrace":
        case "ShowStackTrace": return target.isShowStackTrace();
        case "showstreams":
        case "ShowStreams": return target.isShowStreams();
        case "skipbodylineseparator":
        case "SkipBodyLineSeparator": return target.isSkipBodyLineSeparator();
        case "style":
        case "Style": return target.getStyle();
        default: return null;
        }
    }
}
