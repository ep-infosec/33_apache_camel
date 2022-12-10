/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.hbase;

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
public class HBaseEndpointConfigurer extends PropertyConfigurerSupport implements GeneratedPropertyConfigurer, PropertyConfigurerGetter {

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        HBaseEndpoint target = (HBaseEndpoint) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "bridgeerrorhandler":
        case "bridgeErrorHandler": target.setBridgeErrorHandler(property(camelContext, boolean.class, value)); return true;
        case "cellmappingstrategyfactory":
        case "cellMappingStrategyFactory": target.setCellMappingStrategyFactory(property(camelContext, org.apache.camel.component.hbase.mapping.CellMappingStrategyFactory.class, value)); return true;
        case "exceptionhandler":
        case "exceptionHandler": target.setExceptionHandler(property(camelContext, org.apache.camel.spi.ExceptionHandler.class, value)); return true;
        case "exchangepattern":
        case "exchangePattern": target.setExchangePattern(property(camelContext, org.apache.camel.ExchangePattern.class, value)); return true;
        case "filters": target.setFilters(property(camelContext, java.util.List.class, value)); return true;
        case "lazystartproducer":
        case "lazyStartProducer": target.setLazyStartProducer(property(camelContext, boolean.class, value)); return true;
        case "mappingstrategyclassname":
        case "mappingStrategyClassName": target.setMappingStrategyClassName(property(camelContext, java.lang.String.class, value)); return true;
        case "mappingstrategyname":
        case "mappingStrategyName": target.setMappingStrategyName(property(camelContext, java.lang.String.class, value)); return true;
        case "maxmessagesperpoll":
        case "maxMessagesPerPoll": target.setMaxMessagesPerPoll(property(camelContext, int.class, value)); return true;
        case "maxresults":
        case "maxResults": target.setMaxResults(property(camelContext, int.class, value)); return true;
        case "operation": target.setOperation(property(camelContext, java.lang.String.class, value)); return true;
        case "remove": target.setRemove(property(camelContext, boolean.class, value)); return true;
        case "removehandler":
        case "removeHandler": target.setRemoveHandler(property(camelContext, org.apache.camel.component.hbase.HBaseRemoveHandler.class, value)); return true;
        case "rowmapping":
        case "rowMapping": target.setRowMapping(property(camelContext, java.util.Map.class, value)); return true;
        case "rowmodel":
        case "rowModel": target.setRowModel(property(camelContext, org.apache.camel.component.hbase.model.HBaseRow.class, value)); return true;
        case "usergroupinformation":
        case "userGroupInformation": target.setUserGroupInformation(property(camelContext, org.apache.hadoop.security.UserGroupInformation.class, value)); return true;
        default: return false;
        }
    }

    @Override
    public Class<?> getOptionType(String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "bridgeerrorhandler":
        case "bridgeErrorHandler": return boolean.class;
        case "cellmappingstrategyfactory":
        case "cellMappingStrategyFactory": return org.apache.camel.component.hbase.mapping.CellMappingStrategyFactory.class;
        case "exceptionhandler":
        case "exceptionHandler": return org.apache.camel.spi.ExceptionHandler.class;
        case "exchangepattern":
        case "exchangePattern": return org.apache.camel.ExchangePattern.class;
        case "filters": return java.util.List.class;
        case "lazystartproducer":
        case "lazyStartProducer": return boolean.class;
        case "mappingstrategyclassname":
        case "mappingStrategyClassName": return java.lang.String.class;
        case "mappingstrategyname":
        case "mappingStrategyName": return java.lang.String.class;
        case "maxmessagesperpoll":
        case "maxMessagesPerPoll": return int.class;
        case "maxresults":
        case "maxResults": return int.class;
        case "operation": return java.lang.String.class;
        case "remove": return boolean.class;
        case "removehandler":
        case "removeHandler": return org.apache.camel.component.hbase.HBaseRemoveHandler.class;
        case "rowmapping":
        case "rowMapping": return java.util.Map.class;
        case "rowmodel":
        case "rowModel": return org.apache.camel.component.hbase.model.HBaseRow.class;
        case "usergroupinformation":
        case "userGroupInformation": return org.apache.hadoop.security.UserGroupInformation.class;
        default: return null;
        }
    }

    @Override
    public Object getOptionValue(Object obj, String name, boolean ignoreCase) {
        HBaseEndpoint target = (HBaseEndpoint) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "bridgeerrorhandler":
        case "bridgeErrorHandler": return target.isBridgeErrorHandler();
        case "cellmappingstrategyfactory":
        case "cellMappingStrategyFactory": return target.getCellMappingStrategyFactory();
        case "exceptionhandler":
        case "exceptionHandler": return target.getExceptionHandler();
        case "exchangepattern":
        case "exchangePattern": return target.getExchangePattern();
        case "filters": return target.getFilters();
        case "lazystartproducer":
        case "lazyStartProducer": return target.isLazyStartProducer();
        case "mappingstrategyclassname":
        case "mappingStrategyClassName": return target.getMappingStrategyClassName();
        case "mappingstrategyname":
        case "mappingStrategyName": return target.getMappingStrategyName();
        case "maxmessagesperpoll":
        case "maxMessagesPerPoll": return target.getMaxMessagesPerPoll();
        case "maxresults":
        case "maxResults": return target.getMaxResults();
        case "operation": return target.getOperation();
        case "remove": return target.isRemove();
        case "removehandler":
        case "removeHandler": return target.getRemoveHandler();
        case "rowmapping":
        case "rowMapping": return target.getRowMapping();
        case "rowmodel":
        case "rowModel": return target.getRowModel();
        case "usergroupinformation":
        case "userGroupInformation": return target.getUserGroupInformation();
        default: return null;
        }
    }

    @Override
    public Object getCollectionValueType(Object target, String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "filters": return org.apache.hadoop.hbase.filter.Filter.class;
        case "rowmapping":
        case "rowMapping": return java.lang.Object.class;
        default: return null;
        }
    }
}

