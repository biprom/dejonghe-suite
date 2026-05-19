package com.adverto.dejonghe.application.tests;

import com.adverto.dejonghe.server.Controllers.GoogleRestController;
import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.repos.CustomerImportRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TestsCustomers {
    CustomerImportRepo customerImportRepo;
    CustomerService customerService;
    GoogleRestController googleRestController;

    @Autowired
    private ProductService productService;

    @Autowired
    public TestsCustomers(CustomerImportRepo customerImportRepo,
                          CustomerService customerService,
                          GoogleRestController googleRestController) {
        this.customerImportRepo = customerImportRepo;
        this.customerService = customerService;
        this.googleRestController = googleRestController;
    }

//    @Test
//    void copyCommenArtNumbersPurchasePrice() {
//            List<Customer>customerList = customerService.getAllCustomers().get();
//            for (Customer customer : customerList) {
//                if((customer.getBIndustry() == false) && (customer.getBAgro() == false)) {
//                    System.out.println(customer.getName() + " is geen Agro of Industry");
//                }
//            }
//        }

    @Test
    void setUniquePrice() {
        List<Customer>customerList = customerService.getAllCustomers().get();
        for (Customer customer : customerList) {
            customer.getAddresses().stream().filter(address -> address.getInvoiceAddress() != null && (address.getInvoiceAddress() == false)).forEach(address -> {
                if(address.getAddressName() != null && address.getAddressName().length() > 0) {
                   //do nothing
                }
                else{
                    System.out.println(customer.getName() + " -> " + address.getAddressName());
                    address.setAddressName(customer.getName());
                    customerService.save(customer);
                }
            });
        }
    }
}
