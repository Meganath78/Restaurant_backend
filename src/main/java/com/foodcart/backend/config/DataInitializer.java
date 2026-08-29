package com.foodcart.backend.config;

import com.foodcart.backend.entity.User;
import com.foodcart.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository.findByUsername("admin").isEmpty()) {

                User admin = new User();

                admin.setFirstName("Food");
                admin.setLastName("Cart");
                admin.setUsername("admin");

                admin.setPassword(
                        passwordEncoder.encode("admin123")
                );

                admin.setRole("ADMIN");

                userRepository.save(admin);

                System.out.println("Admin user created");
            }
        };
    }
}