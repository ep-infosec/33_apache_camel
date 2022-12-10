/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.bean;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.ExtendedPropertyConfigurerGetter;
import org.apache.camel.spi.PropertyConfigurerGetter;
import org.apache.camel.spi.ConfigurerStrategy;
import org.apache.camel.spi.GeneratedPropertyConfigurer;
import org.apache.camel.util.CaseInsensitiveMap;
import org.apache.camel.support.component.PropertyConfigurerSupport;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@SuppressWarnings("unchecked")
public class BeanEndpointConfigurer extends PropertyConfigurerSupport implements GeneratedPropertyConfigurer, PropertyConfigurerGetter {

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        BeanEndpoint target = (BeanEndpoint) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "cache": target.setCache(property(camelContext, java.lang.Boolean.class, value)); return true;
        case "lazystartproducer":
        case "lazyStartProducer": target.setLazyStartProducer(property(camelContext, boolean.class, value)); return true;
        case "method": target.setMethod(property(camelContext, java.lang.String.class, value)); return true;
        case "parameters": target.setParameters(property(camelContext, java.util.Map.class, value)); return true;
        case "scope": target.setScope(property(camelContext, org.apache.camel.BeanScope.class, value)); return true;
        default: return false;
        }
    }

    @Override
    public Class<?> getOptionType(String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "cache": return java.lang.Boolean.class;
        case "lazystartproducer":
        case "lazyStartProducer": return boolean.class;
        case "method": return java.lang.String.class;
        case "parameters": return java.util.Map.class;
        case "scope": return org.apache.camel.BeanScope.class;
        default: return null;
        }
    }

    @Override
    public Object getOptionValue(Object obj, String name, boolean ignoreCase) {
        BeanEndpoint target = (BeanEndpoint) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "cache": return target.getCache();
        case "lazystartproducer":
        case "lazyStartProducer": return target.isLazyStartProducer();
        case "method": return target.getMethod();
        case "parameters": return target.getParameters();
        case "scope": return target.getScope();
        default: return null;
        }
    }

    @Override
    public Object getCollectionValueType(Object target, String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "parameters": return java.lang.Object.class;
        default: return null;
        }
    }
}

