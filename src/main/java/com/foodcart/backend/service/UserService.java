package com.foodcart.backend.service;

import com.foodcart.backend.dto.LoginRequest;
import com.foodcart.backend.dto.RegisterRequest;
import com.foodcart.backend.entity.User;
import com.foodcart.backend.exception.DuplicateUsernameException;
import com.foodcart.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register new user
    public User registerUser(RegisterRequest request) {

        // Check duplicate username
        if (userRepository
                .findByUsername(request.getUsername())
                .isPresent()) {

            throw new DuplicateUsernameException(
                    "Username already exists"
            );
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());

        // Hash password before saving
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Every newly registered account is USER
        user.setRole("USER");

        return userRepository.save(user);
    }

    // Login user
    public User loginUser(LoginRequest loginRequest) {

        User user = userRepository
                .findByUsername(loginRequest.getUsername())
                .orElse(null);

        if (user != null &&
                passwordEncoder.matches(
                        loginRequest.getPassword(),
                        user.getPassword())) {

            return user;
        }

        return null;
    }
}