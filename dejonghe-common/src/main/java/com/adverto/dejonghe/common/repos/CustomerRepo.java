package com.adverto.dejonghe.common.repos;

import com.adverto.dejonghe.common.entities.customers.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepo extends MongoRepository<Customer, String> {
    List<Customer> findByNameContainingIgnoreCaseOrVatNumberContainingIgnoreCaseOrAddresses_addressNameContainingIgnoreCase(String nameFilter,String vatFilter,String addressFilter);
    List<Customer> findByNameEqualsIgnoreCase(String customerName);
    List<Customer> findByAddresses_AddressName(String addressName);
}
