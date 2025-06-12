package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.CouponDto;
import itbrains.az.edu.vegetables.models.Coupon;
import itbrains.az.edu.vegetables.repositories.CouponRepository;
import itbrains.az.edu.vegetables.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public Optional<CouponDto> validateCoupon(String code) {
        Optional<Coupon> couponOpt = couponRepository.findByCodeAndActiveTrue(code);

        return couponOpt.map(coupon ->
                new CouponDto(
                        coupon.getCode(),
                        coupon.getDiscountPercentage(),
                        coupon.isActive()
                )
        );
    }
}
