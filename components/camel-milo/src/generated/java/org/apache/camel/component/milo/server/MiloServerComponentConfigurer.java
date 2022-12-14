/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.milo.server;

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
public class MiloServerComponentConfigurer extends PropertyConfigurerSupport implements GeneratedPropertyConfigurer, PropertyConfigurerGetter {

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        MiloServerComponent target = (MiloServerComponent) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "applicationname":
        case "applicationName": target.setApplicationName(property(camelContext, java.lang.String.class, value)); return true;
        case "applicationuri":
        case "applicationUri": target.setApplicationUri(property(camelContext, java.lang.String.class, value)); return true;
        case "autowiredenabled":
        case "autowiredEnabled": target.setAutowiredEnabled(property(camelContext, boolean.class, value)); return true;
        case "bindaddresses":
        case "bindAddresses": target.setBindAddresses(property(camelContext, java.lang.String.class, value)); return true;
        case "bridgeerrorhandler":
        case "bridgeErrorHandler": target.setBridgeErrorHandler(property(camelContext, boolean.class, value)); return true;
        case "buildinfo":
        case "buildInfo": target.setBuildInfo(property(camelContext, org.eclipse.milo.opcua.stack.core.types.structured.BuildInfo.class, value)); return true;
        case "certificate": target.setCertificate(property(camelContext, java.security.cert.X509Certificate.class, value)); return true;
        case "certificatemanager":
        case "certificateManager": target.setCertificateManager(property(camelContext, org.eclipse.milo.opcua.stack.core.security.CertificateManager.class, value)); return true;
        case "certificatevalidator":
        case "certificateValidator": target.setCertificateValidator(property(camelContext, org.eclipse.milo.opcua.stack.server.security.ServerCertificateValidator.class, value)); return true;
        case "defaultcertificatevalidator":
        case "defaultCertificateValidator": target.setDefaultCertificateValidator(property(camelContext, java.lang.String.class, value)); return true;
        case "enableanonymousauthentication":
        case "enableAnonymousAuthentication": target.setEnableAnonymousAuthentication(property(camelContext, boolean.class, value)); return true;
        case "lazystartproducer":
        case "lazyStartProducer": target.setLazyStartProducer(property(camelContext, boolean.class, value)); return true;
        case "namespaceuri":
        case "namespaceUri": target.setNamespaceUri(property(camelContext, java.lang.String.class, value)); return true;
        case "path": target.setPath(property(camelContext, java.lang.String.class, value)); return true;
        case "port": target.setPort(property(camelContext, int.class, value)); return true;
        case "producturi":
        case "productUri": target.setProductUri(property(camelContext, java.lang.String.class, value)); return true;
        case "securitypolicies":
        case "securityPolicies": target.setSecurityPolicies(property(camelContext, java.util.Set.class, value)); return true;
        case "securitypoliciesbyid":
        case "securityPoliciesById": target.setSecurityPoliciesById(property(camelContext, java.lang.String.class, value)); return true;
        case "userauthenticationcredentials":
        case "userAuthenticationCredentials": target.setUserAuthenticationCredentials(property(camelContext, java.lang.String.class, value)); return true;
        case "usernamesecuritypolicyuri":
        case "usernameSecurityPolicyUri": target.setUsernameSecurityPolicyUri(property(camelContext, org.eclipse.milo.opcua.stack.core.security.SecurityPolicy.class, value)); return true;
        default: return false;
        }
    }

    @Override
    public Class<?> getOptionType(String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "applicationname":
        case "applicationName": return java.lang.String.class;
        case "applicationuri":
        case "applicationUri": return java.lang.String.class;
        case "autowiredenabled":
        case "autowiredEnabled": return boolean.class;
        case "bindaddresses":
        case "bindAddresses": return java.lang.String.class;
        case "bridgeerrorhandler":
        case "bridgeErrorHandler": return boolean.class;
        case "buildinfo":
        case "buildInfo": return org.eclipse.milo.opcua.stack.core.types.structured.BuildInfo.class;
        case "certificate": return java.security.cert.X509Certificate.class;
        case "certificatemanager":
        case "certificateManager": return org.eclipse.milo.opcua.stack.core.security.CertificateManager.class;
        case "certificatevalidator":
        case "certificateValidator": return org.eclipse.milo.opcua.stack.server.security.ServerCertificateValidator.class;
        case "defaultcertificatevalidator":
        case "defaultCertificateValidator": return java.lang.String.class;
        case "enableanonymousauthentication":
        case "enableAnonymousAuthentication": return boolean.class;
        case "lazystartproducer":
        case "lazyStartProducer": return boolean.class;
        case "namespaceuri":
        case "namespaceUri": return java.lang.String.class;
        case "path": return java.lang.String.class;
        case "port": return int.class;
        case "producturi":
        case "productUri": return java.lang.String.class;
        case "securitypolicies":
        case "securityPolicies": return java.util.Set.class;
        case "securitypoliciesbyid":
        case "securityPoliciesById": return java.lang.String.class;
        case "userauthenticationcredentials":
        case "userAuthenticationCredentials": return java.lang.String.class;
        case "usernamesecuritypolicyuri":
        case "usernameSecurityPolicyUri": return org.eclipse.milo.opcua.stack.core.security.SecurityPolicy.class;
        default: return null;
        }
    }

    @Override
    public Object getOptionValue(Object obj, String name, boolean ignoreCase) {
        MiloServerComponent target = (MiloServerComponent) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "applicationname":
        case "applicationName": return target.getApplicationName();
        case "applicationuri":
        case "applicationUri": return target.getApplicationUri();
        case "autowiredenabled":
        case "autowiredEnabled": return target.isAutowiredEnabled();
        case "bindaddresses":
        case "bindAddresses": return target.getBindAddresses();
        case "bridgeerrorhandler":
        case "bridgeErrorHandler": return target.isBridgeErrorHandler();
        case "buildinfo":
        case "buildInfo": return target.getBuildInfo();
        case "certificate": return target.getCertificate();
        case "certificatemanager":
        case "certificateManager": return target.getCertificateManager();
        case "certificatevalidator":
        case "certificateValidator": return target.getCertificateValidator();
        case "defaultcertificatevalidator":
        case "defaultCertificateValidator": return target.getDefaultCertificateValidator();
        case "enableanonymousauthentication":
        case "enableAnonymousAuthentication": return target.isEnableAnonymousAuthentication();
        case "lazystartproducer":
        case "lazyStartProducer": return target.isLazyStartProducer();
        case "namespaceuri":
        case "namespaceUri": return target.getNamespaceUri();
        case "path": return target.getPath();
        case "port": return target.getPort();
        case "producturi":
        case "productUri": return target.getProductUri();
        case "securitypolicies":
        case "securityPolicies": return target.getSecurityPolicies();
        case "securitypoliciesbyid":
        case "securityPoliciesById": return target.getSecurityPoliciesById();
        case "userauthenticationcredentials":
        case "userAuthenticationCredentials": return target.getUserAuthenticationCredentials();
        case "usernamesecuritypolicyuri":
        case "usernameSecurityPolicyUri": return target.getUsernameSecurityPolicyUri();
        default: return null;
        }
    }

    @Override
    public Object getCollectionValueType(Object target, String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "securitypolicies":
        case "securityPolicies": return org.eclipse.milo.opcua.stack.core.security.SecurityPolicy.class;
        default: return null;
        }
    }
}

