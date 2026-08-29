package com.foodcart.backend.service;

import com.foodcart.backend.entity.Food;
import com.foodcart.backend.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food> getFoods(
            String category,
            String search,
            Boolean available) {

        List<Food> foods = foodRepository.findAll();

        return foods.stream()
                .filter(food ->
                        category == null ||
                        category.isBlank() ||
                        food.getCategory().equalsIgnoreCase(category))
                .filter(food ->
                        search == null ||
                        search.isBlank() ||
                        food.getName()
                                .toLowerCase()
                                .contains(search.toLowerCase()))
                .filter(food ->
                        available == null ||
                        food.isAvailable() == available)
                .toList();
    }

    public Food getFoodById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    public Food addFood(Food food) {
        return foodRepository.save(food);
    }

    public Food updateFood(Long id, Food foodDetails) {

        Food food = foodRepository.findById(id).orElse(null);

        if (food == null) {
            return null;
        }

        food.setName(foodDetails.getName());
        food.setPrice(foodDetails.getPrice());
        food.setImage(foodDetails.getImage());
        food.setCategory(foodDetails.getCategory());
        food.setRating(foodDetails.getRating());
        food.setDescription(foodDetails.getDescription());
        food.setVeg(foodDetails.isVeg());
        food.setAvailable(foodDetails.isAvailable());

        return foodRepository.save(food);
    }

    public boolean deleteFood(Long id) {

        if (!foodRepository.existsById(id)) {
            return false;
        }

        foodRepository.deleteById(id);
        return true;
    }
}