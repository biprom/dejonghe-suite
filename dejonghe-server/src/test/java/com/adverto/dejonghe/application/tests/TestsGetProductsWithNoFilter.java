package com.adverto.dejonghe.application.tests;

import com.adverto.dejonghe.server.Controllers.GoogleRestController;
import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.adverto.dejonghe.common.repos.CustomerImportRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class TestsGetProductsWithNoFilter {
    CustomerImportRepo customerImportRepo;
    CustomerService customerService;
    GoogleRestController googleRestController;

    @Autowired
    private ProductService productService;

    @Autowired
    public TestsGetProductsWithNoFilter(CustomerImportRepo customerImportRepo,
                                        CustomerService customerService,
                                        GoogleRestController googleRestController) {
        this.customerImportRepo = customerImportRepo;
        this.customerService = customerService;
        this.googleRestController = googleRestController;
    }

    @Test
    void setUpCustomers(){
        Optional<List<Product>> allProducts = productService.getAllProducts();
        if(allProducts.isPresent()){
            for(Product product : allProducts.get()){
                if((product.getProductLevel1() != null) && (product.getProductLevel2() == null)){
                    System.out.println(product.getProductCode() + " " + product.getInternalName());
                }
            }
        }
    }
}