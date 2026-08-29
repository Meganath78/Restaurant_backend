package com.foodcart.backend.controller;

import com.foodcart.backend.entity.Voucher;
import com.foodcart.backend.service.VoucherService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vouchers")
@CrossOrigin(origins = "http://localhost:5173")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // Get all vouchers
    @GetMapping
    public ResponseEntity<List<Voucher>> getAllVouchers() {

        return ResponseEntity.ok(
                voucherService.getAllVouchers()
        );
    }

    // Get voucher by code
    @GetMapping("/{code}")
    public ResponseEntity<Voucher> getVoucherByCode(
            @PathVariable String code) {

        return ResponseEntity.ok(
                voucherService.getVoucherByCode(code)
        );
    }

    // Create voucher
    @PostMapping
    public ResponseEntity<Voucher> createVoucher(
            @RequestBody Voucher voucher) {

        return ResponseEntity.ok(
                voucherService.createVoucher(voucher)
        );
    }

    // Update voucher
    @PutMapping("/{id}")
    public ResponseEntity<Voucher> updateVoucher(
            @PathVariable Long id,
            @RequestBody Voucher voucher) {

        return ResponseEntity.ok(
                voucherService.updateVoucher(id, voucher)
        );
    }

    // Delete voucher
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(
            @PathVariable Long id) {

        voucherService.deleteVoucher(id);

        return ResponseEntity.noContent().build();
    }

    // Validate voucher
    @PostMapping("/validate")
    public ResponseEntity<Voucher> validateVoucher(
            @RequestParam String code,
            @RequestParam BigDecimal orderAmount) {

        return ResponseEntity.ok(
                voucherService.validateVoucher(
                        code,
                        orderAmount
                )
        );
    }

    // Calculate discount
    @PostMapping("/discount")
    public ResponseEntity<Map<String, BigDecimal>> calculateDiscount(
            @RequestParam String code,
            @RequestParam BigDecimal orderAmount) {

        Voucher voucher =
                voucherService.validateVoucher(
                        code,
                        orderAmount
                );

        BigDecimal discount =
                voucherService.calculateDiscount(
                        voucher,
                        orderAmount
                );

        return ResponseEntity.ok(
                Map.of(
                        "discount", discount,
                        "finalAmount",
                        orderAmount.subtract(discount)
                )
        );
    }
}