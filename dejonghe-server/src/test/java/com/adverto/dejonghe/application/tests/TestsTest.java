package com.adverto.dejonghe.application.tests;

import com.adverto.dejonghe.server.Application;
import com.adverto.dejonghe.server.Controllers.GoogleRestController;
import com.adverto.dejonghe.common.dbservices.CustomerService;
import com.adverto.dejonghe.common.dbservices.ProductService;
import com.adverto.dejonghe.common.entities.customers.Address;
import com.adverto.dejonghe.common.entities.customers.Coordinates;
import com.adverto.dejonghe.common.entities.customers.Customer;
import com.adverto.dejonghe.common.entities.customers.CustomerImport;
import com.adverto.dejonghe.common.repos.CustomerImportRepo;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest(classes = Application.class)
class Tests{
    CustomerImportRepo customerImportRepo;
    CustomerService customerService;
    GoogleRestController googleRestController;

    @Autowired
    private ProductService productService;

    @Autowired
    public Tests(CustomerImportRepo customerImportRepo,
                 CustomerService customerService,
                 GoogleRestController googleRestController) {
        this.customerImportRepo = customerImportRepo;
        this.customerService = customerService;
        this.googleRestController = googleRestController;
    }

//    @Test
//    void setUpCustomers(){
//        Optional<List<Customer>> allCustomers = customerService.getAllCustomers();
//        allCustomers.ifPresent(customers -> {
//            for (Customer customer : customers) {
//                if(customer.getAddresses().size() == 1){
//                    Address originalAddress = customer.getAddresses().get(0);
//                    Address copyAddress = originalAddress.clone();
//
//                    originalAddress.setInvoiceAddress(true);
//                    copyAddress.setInvoiceAddress(false);
//
//                    customer.getAddresses().add(copyAddress);
//                    customerService.save(customer);
//                }
//                else{
//                    //DO nothing fucker!
//                }
//            }
//        });
//    }



//    @Test
//    void copyCommenArtNumbersPurchasePrice() {
//        List<Product>productList = productService.getAllProducts().get();
//        int i = 0;
//        for(Product product:productList){
//            if((product.getProductCode() != null) && (product.getProductCode().length() > 0)){
//                Optional<List<Product>>commonProductList = productService.findByProductCodeEqualCaseInsensitive(product.getProductCode().toLowerCase());
//                if((commonProductList.get() != null) && (commonProductList.get().size() > 0)){
//                    Optional<Double> maxPurchasePrice = commonProductList.get().stream().filter(item -> item.getPurchasePrice() != null).map(item -> item.getPurchasePrice()).max(Double::compareTo);
//                    Optional<Product> optInternalName = commonProductList.get().stream().filter(item -> (item.getInternalName() != null) && (item.getInternalName().length() > 0)).findFirst();
//                    Optional<Product> optComment = commonProductList.get().stream().filter(item -> (item.getComment() != null) && (item.getComment().length() > 0)).findFirst();
//                    if(maxPurchasePrice.isPresent()){
//                        for(Product commonProduct:commonProductList.get()){
//                            commonProduct.setPurchasePrice(maxPurchasePrice.get());
//                            if(commonProduct.getSellMargin() != null){
//                                commonProduct.setSellPrice(commonProduct.getSellMargin()*commonProduct.getPurchasePrice());
//                            }
//                            if(optInternalName.isPresent()){
//                                commonProduct.setInternalName(optInternalName.get().getInternalName());
//                            }
//                            productService.save(commonProduct);
//                            System.out.println(i + " " +commonProduct.getProductCode() + " " + commonProduct.getPurchasePrice() + " " + commonProduct.getSellMargin() + " " + commonProduct.getSellPrice());
//                        }
//                    }
//                }
//                }
//            }
//        }


    //@Test
    void addCustomersImportToCustomers() {
        List<CustomerImport> customerImportList = customerImportRepo.findAll();
        for (CustomerImport customerImport : customerImportList) {
            Customer customer = new Customer();
            customer.setName(customerImport.getName());
            customer.setVatNumber(customerImport.getVatNumber());
            customer.setBAgro(false);
            customer.setBIndustry(false);

            Address address = new Address();
            address.setInvoiceAddress(true);
            address.setZip(customerImport.getZip());
            address.setCity(customerImport.getCity());
            address.setCountry(customerImport.getCountry());
            address.setStreet(customerImport.getStreet());
            List<Address> addressList = new ArrayList<>();
            addressList.add(address);

            customer.setAddresses(addressList);

            customerService.save(customer);

        }
    }

    //@Test
    void setAllDistancesToCustomers() throws JSONException {
        Optional<List<Customer>> optCustomerList = customerService.getAllCustomers();
        if (optCustomerList.isPresent()) {
            for (Customer customer : optCustomerList.get()) {
                for (Address address : customer.getAddresses()) {
                    //add Distance x2
                    Optional<Double> optDistance = googleRestController.getOptDistanceforAdres(customer.getAddresses().get(0));
                    if (optDistance.isPresent()) {
                        address.setDistance(2*optDistance.get());
                    }
                    //add Coordinates
                    Optional<Coordinates> optCoordinates = googleRestController.getOptCoordinatesforAdres(customer.getAddresses().get(0));
                    if (optCoordinates.isPresent()) {
                        address.setCoordinates(optCoordinates.get());
                    }
                }
                customerService.save(customer);
            }
        }
    }

    @Test
    void getCustomersWithoutWorkAddress(){
        Optional<List<Customer>> allCustomers = customerService.getAllCustomers();
        allCustomers.ifPresent(customers -> {
            for (Customer customer : customers) {
                if((customer.getAddresses() != null)){
                    System.out.println(customer.getName() + " Aantal adressen : " + customer.getAddresses().size());
                }
            }
        });
    }
}