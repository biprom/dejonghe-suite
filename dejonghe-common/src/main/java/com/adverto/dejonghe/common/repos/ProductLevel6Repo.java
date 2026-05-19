package com.adverto.dejonghe.common.repos;

import com.adverto.dejonghe.common.entities.product.product.ProductLevel6;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductLevel6Repo extends MongoRepository<ProductLevel6, String> {
    Long removeById(String id);
    @Query(value="{ 'productLevel5.name' : ?0 ,  'productLevel5.productLevel4.name' : ?1 , 'productLevel5.productLevel4.productLevel3.name' : ?2 , 'productLevel5.productLevel4.productLevel3.productLevel2.name' : ?3 , 'productLevel5.productLevel4.productLevel3.productLevel2.productLevel1.name' : ?4 }")
    List<ProductLevel6> findByPreviousLevelNames(String productlevel5,
                                                 String productlevel4,
                                                 String productlevel3,
                                                 String productlevel2,
                                                 String productlevel1);
}
