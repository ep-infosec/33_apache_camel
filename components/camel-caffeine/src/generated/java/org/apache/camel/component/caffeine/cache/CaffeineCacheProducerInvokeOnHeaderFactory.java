/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.caffeine.cache;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.spi.InvokeOnHeaderStrategy;
import org.apache.camel.component.caffeine.cache.CaffeineCacheProducer;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@SuppressWarnings("unchecked")
public class CaffeineCacheProducerInvokeOnHeaderFactory implements InvokeOnHeaderStrategy {

    @Override
    public Object invoke(Object obj, String key, Exchange exchange, AsyncCallback callback) throws Exception {
        org.apache.camel.component.caffeine.cache.CaffeineCacheProducer target = (org.apache.camel.component.caffeine.cache.CaffeineCacheProducer) obj;
        switch (key) {
        case "as_map":
        case "AS_MAP": target.onAsMap(exchange.getMessage()); return null;
        case "cleanup":
        case "CLEANUP": target.onCleanUp(exchange.getMessage()); return null;
        case "get":
        case "GET": target.onGet(exchange.getMessage()); return null;
        case "get_all":
        case "GET_ALL": target.onGetAll(exchange.getMessage()); return null;
        case "invalidate":
        case "INVALIDATE": target.onInvalidate(exchange.getMessage()); return null;
        case "invalidate_all":
        case "INVALIDATE_ALL": target.onInvalidateAll(exchange.getMessage()); return null;
        case "put":
        case "PUT": target.onPut(exchange.getMessage()); return null;
        case "put_all":
        case "PUT_ALL": target.onPutAll(exchange.getMessage()); return null;
        default: return null;
        }
    }

}

