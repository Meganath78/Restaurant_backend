package com.foodcart.backend.service;

import com.foodcart.backend.entity.CartItem;
import com.foodcart.backend.entity.Food;
import com.foodcart.backend.entity.User;
import com.foodcart.backend.exception.InvalidQuantityException;
import com.foodcart.backend.repository.CartItemRepository;
import com.foodcart.backend.repository.FoodRepository;
import com.foodcart.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    public CartService(
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            FoodRepository foodRepository) {

        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.foodRepository = foodRepository;
    }

    // Get user's cart
    public List<CartItem> getCart(String username) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return cartItemRepository.findByUser(user);
    }

    // Add food to cart
    public CartItem addToCart(
            String username,
            Long foodId,
            int quantity) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Food food = foodRepository
                .findById(foodId)
                .orElseThrow(() ->
                        new RuntimeException("Food not found"));

        if (quantity <= 0) {
            throw new InvalidQuantityException(
                    "Quantity must be greater than 0");
        }

        CartItem existingItem =
                cartItemRepository
                        .findByUserAndFood(user, food)
                        .orElse(null);

        if (existingItem != null) {

            existingItem.setQuantity(
                    existingItem.getQuantity() + quantity
            );

            return cartItemRepository.save(existingItem);
        }

        CartItem cartItem =
                new CartItem(user, food, quantity);

        return cartItemRepository.save(cartItem);
    }

    // Update cart item quantity
    public CartItem updateQuantity(
            String username,
            Long cartItemId,
            int quantity) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        CartItem cartItem =
                cartItemRepository
                        .findById(cartItemId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cart item not found"));

        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new InvalidQuantityException(
                    "You cannot modify another user's cart");
        }

        if (quantity <= 0) {
            throw new RuntimeException(
                    "Quantity must be greater than 0");
        }

        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    // Remove one item from cart
    public void removeFromCart(
            String username,
            Long cartItemId) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        CartItem cartItem =
                cartItemRepository
                        .findById(cartItemId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cart item not found"));

        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new RuntimeException(
                    "You cannot modify another user's cart");
        }

        cartItemRepository.delete(cartItem);
    }

    // Clear entire cart
    public void clearCart(String username) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<CartItem> cartItems =
                cartItemRepository.findByUser(user);

        cartItemRepository.deleteAll(cartItems);
    }
}