package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.coupon.CouponDto;
import itbrains.az.edu.vegetables.dtos.coupon.CouponCreateDto;
import itbrains.az.edu.vegetables.dtos.coupon.CouponDashboardDto;
import itbrains.az.edu.vegetables.dtos.coupon.CouponUpdateDto;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    Optional<CouponDto> validateCoupon(String code);

    List<CouponDashboardDto> getDashboardCoupon();

    void createCoupon(CouponCreateDto couponCreateDto);

    CouponUpdateDto getUpdateCoupon(Long id);

    void updateCoupon(Long id, CouponUpdateDto couponUpdateDto);

    void deleteCoupon(Long id);
}
