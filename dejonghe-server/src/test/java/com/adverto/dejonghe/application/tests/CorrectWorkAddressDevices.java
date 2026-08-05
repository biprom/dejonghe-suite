package com.adverto.dejonghe.application.tests;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.repos.CustomerImportRepo;
import com.adverto.dejonghe.server.Application;
import com.adverto.dejonghe.server.Controllers.GoogleRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = Application.class)
class CorrectWorkAddressDevices {

    CustomerService customerService;


    @Autowired
    public CorrectWorkAddressDevices(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Test
    void correctWorkAddressDevicesToRightAddress() {
        List<Customer>customerList = customerService.getAllCustomers().get();
        for (Customer customer : customerList) {
            customer.getAddresses().stream().filter(address -> address.getInvoiceAddress() != null && (address.getInvoiceAddress() == true)).forEach(invoiceAddress -> {
                if((invoiceAddress.getCoupledDeviceList() != null) && (invoiceAddress.getCoupledDeviceList().size() > 0)){
                    System.out.println(customer.getName() + " " + invoiceAddress.getAddressName());
                }
            });
        }
    }
}
