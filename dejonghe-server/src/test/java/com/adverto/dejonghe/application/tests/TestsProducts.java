package com.adverto.dejonghe.application.tests;

import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.product.product.Product;
import com.adverto.dejonghe.common.services.SetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class TestsProducts {


    private ProductService productService;
    private SetService setService;

    @Autowired
    public TestsProducts(ProductService productService,
                         SetService setService) {
       this.productService = productService;
       this.setService = setService;
    }

    @Test
    void ifPurchasePriceIsZeroCopySellPriceAndSetMarginToOne() {
        Optional<List<Product>> allProducts = productService.getAllProducts();
        for(Product product : allProducts.get()) {
            //Copy sellPrice to Zero purchasePrice
            if((product.getPurchasePrice() == null) || (product.getPurchasePrice() == 0.0)) {
                if(!product.getSellPrice().isNaN()){
                    product.setPurchasePrice(product.getSellPrice());
                    product.setSellMargin(1.0);
                }
                else{
                    product.setPurchasePrice(0.0);
                    product.setSellPrice(0.0);
                    product.setSellMargin(1.0);
                }
            }

            if(product.getSellMargin().isNaN()){
                product.setSellMargin(0.0);
            }

            if(product.getSellPrice().isNaN()){
                product.setSellPrice(0.0);
            }
            //Check if product is a Element and is not in a set.
            //if so set Element as false
            if((product.getSetElement() != null) && (product.getSetElement() == true)){
                Optional<List<Product>> allSetsContaining = productService.getAllSetsContaining(product);
                if(!allSetsContaining.isEmpty()){
                    if(!(allSetsContaining.get().size() > 0)){
                        System.out.println(product.getProductCode() + " is een Element en zit niet in een set");
                        product.setSetElement(false);
                    }
                }
            }
            productService.save(product);
        }
    }
}
