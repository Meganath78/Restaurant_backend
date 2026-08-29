package com.foodcart.backend.repository;

import com.foodcart.backend.entity.OrderItem;
import com.foodcart.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);
}