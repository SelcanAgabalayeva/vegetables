package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.CouponDto;

import java.util.Optional;

public interface CouponService {
    Optional<CouponDto> validateCoupon(String code);
}
