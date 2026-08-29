package com.foodcart.backend.controller;

import com.foodcart.backend.dto.AuthResponse;
import com.foodcart.backend.dto.LoginRequest;
import com.foodcart.backend.dto.RegisterRequest;
import com.foodcart.backend.dto.UserResponse;
import com.foodcart.backend.entity.User;
import com.foodcart.backend.service.JwtService;
import com.foodcart.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    

    public AuthController(UserService userService,
                          JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        User savedUser = userService.registerUser(request);

        UserResponse response = new UserResponse(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getUsername(),
                savedUser.getRole()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest loginRequest) {

        User user = userService.loginUser(loginRequest);

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getRole()
        );

        AuthResponse response =
                new AuthResponse(token, userResponse);

        return ResponseEntity.ok(response);
    }
}