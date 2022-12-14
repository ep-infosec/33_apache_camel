/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.dataformat.soap;

import java.util.List;

import com.example.customerservice.Customer;
import com.example.customerservice.CustomerService;
import com.example.customerservice.GetCustomersByName;
import com.example.customerservice.GetCustomersByNameResponse;
import com.example.customerservice.NoSuchCustomer;
import com.example.customerservice.NoSuchCustomerException;
import org.apache.camel.Produce;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.soap.name.ElementNameStrategy;
import org.apache.camel.dataformat.soap.name.ServiceInterfaceStrategy;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Checks for interoperability between a CXF server that is attached using the Camel transport for CXF and a dynamic
 * proxy using the SOAP data format
 */
@CamelSpringTest
@ContextConfiguration
public class SoapCxfServerTest extends RouteBuilder {
    @Produce("direct:camelClient")
    CustomerService customerServiceProxy;

    @Test
    public void testSuccess() throws NoSuchCustomerException {
        GetCustomersByName request = new GetCustomersByName();
        request.setName("test");
        GetCustomersByNameResponse response = customerServiceProxy.getCustomersByName(request);
        assertNotNull(response);
        List<Customer> customers = response.getReturn();
        assertEquals(1, customers.size());
        assertEquals("test", customers.get(0).getName());
    }

    @Test
    public void testFault() {
        GetCustomersByName request = new GetCustomersByName();
        request.setName("none");
        try {
            customerServiceProxy.getCustomersByName(request);
            fail("NoSuchCustomerException expected");
        } catch (NoSuchCustomerException e) {
            NoSuchCustomer info = e.getFaultInfo();
            assertEquals("none", info.getCustomerId());
        }
    }

    @Override
    public void configure() throws Exception {
        String jaxbPackage = GetCustomersByName.class.getPackage().getName();
        ElementNameStrategy elNameStrat = new ServiceInterfaceStrategy(CustomerService.class, true);
        SoapDataFormat soapDataFormat = new SoapDataFormat(jaxbPackage, elNameStrat);
        from("direct:camelClient") //
                .marshal(soapDataFormat) //
                .to("direct:cxfEndpoint") //
                .unmarshal(soapDataFormat);
    }

}
