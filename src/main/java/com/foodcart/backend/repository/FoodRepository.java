package com.foodcart.backend.repository;

import com.foodcart.backend.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

}