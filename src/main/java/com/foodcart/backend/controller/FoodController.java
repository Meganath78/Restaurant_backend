package com.foodcart.backend.controller;

import com.foodcart.backend.entity.Food;
import com.foodcart.backend.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = "http://localhost:5173")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    // Get all foods
    @GetMapping
public ResponseEntity<List<Food>> getFoods(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Boolean available) {

    return ResponseEntity.ok(
            foodService.getFoods(category, search, available)
    );
}

    // Get food by ID
    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) {

        Food food = foodService.getFoodById(id);

        if (food == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(food);
    }

    // Add food
   @PostMapping
public ResponseEntity<Food> addFood(
        @Valid @RequestBody Food food) {

    Food savedFood = foodService.addFood(food);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(savedFood);
}

    // Update food
    @PutMapping("/{id}")
public ResponseEntity<Food> updateFood(
        @PathVariable Long id,
        @Valid @RequestBody Food foodDetails) {

    Food updatedFood =
            foodService.updateFood(id, foodDetails);

    if (updatedFood == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(updatedFood);
}

    // Delete food
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {

        boolean deleted = foodService.deleteFood(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}