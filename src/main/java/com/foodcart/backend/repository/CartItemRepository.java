package com.foodcart.backend.repository;

import com.foodcart.backend.entity.CartItem;
import com.foodcart.backend.entity.User;
import com.foodcart.backend.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndFood(User user, Food food);
}