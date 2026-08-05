package com.adverto.dejonghe.common.dbservices;

import com.adverto.dejonghe.common.entities.product.product.*;
import com.adverto.dejonghe.common.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderRepo orderRepo;

    public Optional<Order> findById(String id) {
        Optional<Order> optionalOrder = orderRepo.findById(id);
        return optionalOrder;
    }

    public Optional<List<Order>> findByOrderCodeContaining(String orderCode) {
        Optional<List<Order>> optionalOrders = Optional.of(orderRepo.findByOrderCodeContainsIgnoreCase(orderCode));
        return optionalOrders;
    }

    public Optional<List<Order>> findByOrderCodeEqualCaseInsensitive(String orderCode) {
        Optional<List<Order>> optionalOrders = Optional.of(orderRepo.findByOrderCodeEqualsIgnoreCase(orderCode));
        return optionalOrders;
    }


    public void delete(Order order) {
        orderRepo.delete(order);
    }

    public void deleteSelecteditems(Set<Order> orderSet) {
        for (Order order : orderSet) {
            orderRepo.delete(order);
        }
    }


    public void save(Order newOrder) {
        orderRepo.save(newOrder);
    }

    public Optional<Order> get(String id) {
        Optional<Order> optionalOrder = orderRepo.findById(id);
        return optionalOrder;
    }

    public Optional<List<Order>> getOrdersById(List<String> idList) {
        Optional<List<Order>> optionalOrder = Optional.of(orderRepo.findAllById(idList));
        return optionalOrder;
    }

    public Optional<List<Order>> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        if (!orders.isEmpty()) {
            return Optional.of(orders);
        } else {
            return Optional.empty();
        }
    }

    public void showRemoveNotification() {

    }
}
