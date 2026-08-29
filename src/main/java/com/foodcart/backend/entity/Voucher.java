package com.foodcart.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vouchers")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Voucher code is required")
    @Column(nullable = false, unique = true)
    private String code;

    @NotNull(message = "Discount percentage is required")
    @DecimalMin(value = "0.01", message = "Discount must be greater than 0")
    @DecimalMax(value = "100.0", message = "Discount cannot be greater than 100")
    private BigDecimal discountPercentage;

    @NotNull(message = "Minimum order amount is required")
    @DecimalMin(value = "0.0", message = "Minimum order amount cannot be negative")
    private BigDecimal minimumOrderAmount;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;

    private boolean active;

    public Voucher() {
    }

    public Voucher(
            String code,
            BigDecimal discountPercentage,
            BigDecimal minimumOrderAmount,
            LocalDate expiryDate,
            boolean active) {

        this.code = code;
        this.discountPercentage = discountPercentage;
        this.minimumOrderAmount = minimumOrderAmount;
        this.expiryDate = expiryDate;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(BigDecimal minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}