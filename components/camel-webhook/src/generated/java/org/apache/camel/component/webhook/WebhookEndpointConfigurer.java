/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.webhook;

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
public class WebhookEndpointConfigurer extends PropertyConfigurerSupport implements GeneratedPropertyConfigurer, PropertyConfigurerGetter {

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        WebhookEndpoint target = (WebhookEndpoint) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "bridgeerrorhandler":
        case "bridgeErrorHandler": target.setBridgeErrorHandler(property(camelContext, boolean.class, value)); return true;
        case "exceptionhandler":
        case "exceptionHandler": target.setExceptionHandler(property(camelContext, org.apache.camel.spi.ExceptionHandler.class, value)); return true;
        case "exchangepattern":
        case "exchangePattern": target.setExchangePattern(property(camelContext, org.apache.camel.ExchangePattern.class, value)); return true;
        case "webhookautoregister":
        case "webhookAutoRegister": target.getConfiguration().setWebhookAutoRegister(property(camelContext, boolean.class, value)); return true;
        case "webhookbasepath":
        case "webhookBasePath": target.getConfiguration().setWebhookBasePath(property(camelContext, java.lang.String.class, value)); return true;
        case "webhookcomponentname":
        case "webhookComponentName": target.getConfiguration().setWebhookComponentName(property(camelContext, java.lang.String.class, value)); return true;
        case "webhookexternalurl":
        case "webhookExternalUrl": target.getConfiguration().setWebhookExternalUrl(property(camelContext, java.lang.String.class, value)); return true;
        case "webhookpath":
        case "webhookPath": target.getConfiguration().setWebhookPath(property(camelContext, java.lang.String.class, value)); return true;
        default: return false;
        }
    }

    @Override
    public Class<?> getOptionType(String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "bridgeerrorhandler":
        case "bridgeErrorHandler": return boolean.class;
        case "exceptionhandler":
        case "exceptionHandler": return org.apache.camel.spi.ExceptionHandler.class;
        case "exchangepattern":
        case "exchangePattern": return org.apache.camel.ExchangePattern.class;
        case "webhookautoregister":
        case "webhookAutoRegister": return boolean.class;
        case "webhookbasepath":
        case "webhookBasePath": return java.lang.String.class;
        case "webhookcomponentname":
        case "webhookComponentName": return java.lang.String.class;
        case "webhookexternalurl":
        case "webhookExternalUrl": return java.lang.String.class;
        case "webhookpath":
        case "webhookPath": return java.lang.String.class;
        default: return null;
        }
    }

    @Override
    public Object getOptionValue(Object obj, String name, boolean ignoreCase) {
        WebhookEndpoint target = (WebhookEndpoint) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "bridgeerrorhandler":
        case "bridgeErrorHandler": return target.isBridgeErrorHandler();
        case "exceptionhandler":
        case "exceptionHandler": return target.getExceptionHandler();
        case "exchangepattern":
        case "exchangePattern": return target.getExchangePattern();
        case "webhookautoregister":
        case "webhookAutoRegister": return target.getConfiguration().isWebhookAutoRegister();
        case "webhookbasepath":
        case "webhookBasePath": return target.getConfiguration().getWebhookBasePath();
        case "webhookcomponentname":
        case "webhookComponentName": return target.getConfiguration().getWebhookComponentName();
        case "webhookexternalurl":
        case "webhookExternalUrl": return target.getConfiguration().getWebhookExternalUrl();
        case "webhookpath":
        case "webhookPath": return target.getConfiguration().getWebhookPath();
        default: return null;
        }
    }
}

