package com.foodcart.backend.controller;

import com.foodcart.backend.entity.CartItem;
import com.foodcart.backend.service.CartService;



import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173")

public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Get current user's cart
    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                cartService.getCart(username)
        );
    }

    // Add food to cart
   @PostMapping
public ResponseEntity<CartItem> addToCart(
        Authentication authentication,
        @RequestParam Long foodId,
        @RequestParam int quantity) {

    String username = authentication.getName();

    CartItem cartItem = cartService.addToCart(
            username,
            foodId,
            quantity
    );

    return ResponseEntity.ok(cartItem);
}

    // Update quantity
   @PutMapping("/{cartItemId}")
public ResponseEntity<CartItem> updateQuantity(
        Authentication authentication,
        @PathVariable Long cartItemId,
        @RequestParam int quantity) {

    String username = authentication.getName();

    CartItem updatedItem = cartService.updateQuantity(
            username,
            cartItemId,
            quantity
    );

    return ResponseEntity.ok(updatedItem);
}
    // Remove item
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(
            Authentication authentication,
            @PathVariable Long cartItemId) {

        String username = authentication.getName();

        cartService.removeFromCart(
                username,
                cartItemId
        );

        return ResponseEntity.noContent().build();
    }

    // Clear cart
    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            Authentication authentication) {

        String username = authentication.getName();

        cartService.clearCart(username);

        return ResponseEntity.noContent().build();
    }
}