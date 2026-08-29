package com.foodcart.backend.service;

import com.foodcart.backend.entity.Voucher;
import com.foodcart.backend.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import com.foodcart.backend.exception.DuplicateVoucherException;
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    // Get all vouchers
    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    // Get voucher by code
    public Voucher getVoucherByCode(String code) {

        return voucherRepository.findByCode(code)
                .orElseThrow(() ->
                        new RuntimeException("Voucher not found"));
    }

    // Create voucher
    public Voucher createVoucher(Voucher voucher) {

        String code = voucher.getCode()
                .trim()
                .toUpperCase();

        if (voucherRepository.existsByCode(code)) {
            throw new DuplicateVoucherException(
                    "Voucher code already exists");
        }

        voucher.setCode(code);

        return voucherRepository.save(voucher);
    }

    // Update voucher
    public Voucher updateVoucher(
            Long id,
            Voucher updatedVoucher) {

        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Voucher not found"));

        voucher.setCode(
                updatedVoucher.getCode()
                        .trim()
                        .toUpperCase()
        );

        voucher.setDiscountPercentage(
                updatedVoucher.getDiscountPercentage()
        );

        voucher.setMinimumOrderAmount(
                updatedVoucher.getMinimumOrderAmount()
        );

        voucher.setExpiryDate(
                updatedVoucher.getExpiryDate()
        );

        voucher.setActive(
                updatedVoucher.isActive()
        );

        return voucherRepository.save(voucher);
    }

    // Delete voucher
    public void deleteVoucher(Long id) {

        if (!voucherRepository.existsById(id)) {
            throw new RuntimeException(
                    "Voucher not found");
        }

        voucherRepository.deleteById(id);
    }

    // Validate voucher for an order
    public Voucher validateVoucher(
            String code,
            BigDecimal orderAmount) {

        Voucher voucher = voucherRepository
                .findByCode(code.trim().toUpperCase())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid voucher code"));

        if (!voucher.isActive()) {
            throw new RuntimeException(
                    "Voucher is inactive");
        }

        if (voucher.getExpiryDate()
                .isBefore(LocalDate.now())) {

            throw new RuntimeException(
                    "Voucher has expired");
        }

        if (orderAmount.compareTo(
                voucher.getMinimumOrderAmount()) < 0) {

            throw new RuntimeException(
                    "Minimum order amount is "
                            + voucher.getMinimumOrderAmount());
        }

        return voucher;
    }

    // Calculate discount
    public BigDecimal calculateDiscount(
            Voucher voucher,
            BigDecimal orderAmount) {

        BigDecimal discount =
                orderAmount
                        .multiply(
                                voucher.getDiscountPercentage()
                        )
                        .divide(
                                BigDecimal.valueOf(100),
                                2,
                                RoundingMode.HALF_UP
                        );

        return discount;
    }
}