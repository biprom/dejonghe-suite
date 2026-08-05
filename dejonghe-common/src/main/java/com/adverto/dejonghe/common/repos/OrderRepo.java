package com.adverto.dejonghe.common.repos;

import com.adverto.dejonghe.common.entities.product.product.Order;
import com.adverto.dejonghe.common.entities.product.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrderRepo extends MongoRepository<Order, String> {
    List<Order> findByOrderCodeContainsIgnoreCase(String orderCode);
    List<Order> findByOrderCodeEqualsIgnoreCase(String orderCode);
}
