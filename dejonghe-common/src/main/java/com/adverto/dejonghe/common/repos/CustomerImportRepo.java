package com.adverto.dejonghe.common.repos;

import com.adverto.dejonghe.common.entities.customers.CustomerImport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerImportRepo extends MongoRepository<CustomerImport, String> {
}
