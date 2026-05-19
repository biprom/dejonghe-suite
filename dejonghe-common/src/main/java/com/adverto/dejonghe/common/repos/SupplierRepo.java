package com.adverto.dejonghe.common.repos;

import com.adverto.dejonghe.common.entities.product.product.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SupplierRepo extends MongoRepository<Supplier, String> {
    List<Supplier> getSuppliersByNameContainsIgnoreCase(String name);
}
