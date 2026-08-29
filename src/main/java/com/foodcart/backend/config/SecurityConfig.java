package com.foodcart.backend.config;

import com.foodcart.backend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

        .requestMatchers(
                "/api/auth/register",
                "/api/auth/login"
        ).permitAll()

        .requestMatchers(HttpMethod.GET, "/api/foods/**")
        .hasAnyRole("USER", "ADMIN")

        .requestMatchers(HttpMethod.POST, "/api/foods/**")
        .hasRole("ADMIN")

        .requestMatchers(HttpMethod.PUT, "/api/foods/**")
        .hasRole("ADMIN")

        .requestMatchers(HttpMethod.DELETE, "/api/foods/**")
        .hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/vouchers/**")
.hasAnyRole("USER", "ADMIN")

.requestMatchers(HttpMethod.POST, "/api/vouchers/validate")
.hasAnyRole("USER", "ADMIN")

.requestMatchers(HttpMethod.POST, "/api/vouchers/discount")
.hasAnyRole("USER", "ADMIN")

.requestMatchers(HttpMethod.POST, "/api/vouchers")
.hasRole("ADMIN")

.requestMatchers(HttpMethod.PUT, "/api/vouchers/**")
.hasRole("ADMIN")

.requestMatchers(HttpMethod.DELETE, "/api/vouchers/**")
.hasRole("ADMIN")
.requestMatchers(HttpMethod.POST, "/api/orders")
.hasAnyRole("USER", "ADMIN")

.requestMatchers(HttpMethod.GET, "/api/orders/**")
.hasAnyRole("USER", "ADMIN")

.requestMatchers(HttpMethod.POST, "/api/payments/**")
.hasAnyRole("USER", "ADMIN")

.requestMatchers(HttpMethod.GET, "/api/payments/**")
.hasAnyRole("USER", "ADMIN")


        .anyRequest().authenticated()
)

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}