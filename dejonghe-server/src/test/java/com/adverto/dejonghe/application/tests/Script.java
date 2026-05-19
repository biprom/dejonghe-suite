package com.adverto.dejonghe.application.tests;

import com.adverto.dejonghe.common.dbservices.InvoiceService;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.invoice.Invoice;
import com.adverto.dejonghe.common.entities.product.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class Script {

    InvoiceService invoiceService;
    ProductService productService;

    @Autowired
    public Script(InvoiceService invoiceService,
                  ProductService productService) {
        this.invoiceService = invoiceService;
        this.productService = productService;
    }

    @Test
    void changeCommentLines(){
        Optional<List<Invoice>> all = invoiceService.getAll();
        if(all.isPresent()){
            for(Invoice invoice : all.get()){
                if(invoice.getBFinalInvoice() == false){
                    if(invoice.getProductList() != null && invoice.getProductList().size() > 0){
                        for(Product product : invoice.getProductList()){
                            if((product.getSelectedAmount() == null) || (product.getSelectedAmount() == 0.0)){
                                product.setSellPrice(null);
                                product.setSellPriceIndustry(null);
                                product.setTotalPrice(null);
                                product.setBComment(true);
                            }
                        }
                    }
                    invoiceService.save(invoice);
                }
            }
        }
    }
}