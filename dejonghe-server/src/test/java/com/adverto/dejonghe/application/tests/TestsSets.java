package com.adverto.dejonghe.application.tests;

import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Coordinates;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.entities.customers.CustomerImport;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.adverto.dejonghe.common.repos.CustomerImportRepo;
import com.adverto.dejonghe.common.services.SetService;
import com.adverto.dejonghe.server.Application;
import com.adverto.dejonghe.server.Controllers.GoogleRestController;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest(classes = Application.class)
class TestsSets {

    private ProductService productService;
    private SetService setService;

    @Autowired
    public TestsSets(ProductService productService,
                     SetService setService) {

        this.productService = productService;
        this.setService = setService;

    }

    @Test
    void recalcSets(){
        List<Product> allSets = productService.getAllProducts().get().stream().filter(x -> (x.getSet() != null) && (x.getSet())).collect(Collectors.toList());
        allSets.stream().filter(set -> set.getSetList() != null).forEach(set ->{
            set.getSetList().stream().forEach(item -> {
                if(item.getSelectedAmount() != 1.0){
                    System.out.println(item.getSelectedAmount() + " -> " + set.getProductCode());
                    item.setSellPrice(item.getSelectedAmount() * item.getPurchasePrice() * item.getSellMargin());
                    item.setSellPriceIndustry(item.getSelectedAmount() * item.getPurchasePrice() * item.getSellMarginIndustry());
                }
            });
            set.setPurchasePrice(setService.tryToCalculatePurchasePrice(set));
            set.setSellMargin(setService.tryToCalculateAgroMargin(set));
            set.setSellMarginIndustry(setService.tryToCalculateSellIndustryMargin(set));
            set.setSellPrice(setService.tryToCalculateSellAgroPrice(set));
            set.setSellPriceIndustry(setService.tryToCalculateSellIndustryPrice(set));
            productService.save(set);
        });
    }

    @Autowired
    private Environment environment;

    @Test
    void checkMongoConfiguration() {
        System.out.println(
                "Mongo URI = " +
                        environment.getProperty("spring.mongodb.uri")
        );
    }
}