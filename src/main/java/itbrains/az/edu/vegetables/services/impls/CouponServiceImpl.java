package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.coupon.CouponDto;
import itbrains.az.edu.vegetables.dtos.coupon.CouponCreateDto;
import itbrains.az.edu.vegetables.dtos.coupon.CouponDashboardDto;
import itbrains.az.edu.vegetables.dtos.coupon.CouponUpdateDto;
import itbrains.az.edu.vegetables.models.Coupon;
import itbrains.az.edu.vegetables.repositories.CouponRepository;
import itbrains.az.edu.vegetables.services.CouponService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private final ModelMapper modelMapper;

    public CouponServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

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

    @Override
    public List<CouponDashboardDto> getDashboardCoupon() {
        return couponRepository
                .findAll(Sort.by(Sort.Direction.ASC, "id"))   //
                .stream()
                .map(cou -> modelMapper.map(cou, CouponDashboardDto.class))
                .toList();
    }

    @Override
    public void createCoupon(CouponCreateDto couponCreateDto) {
        Coupon coupon = new Coupon();
        coupon.setCode(couponCreateDto.getCode());
        coupon.setDiscountPercentage(couponCreateDto.getDiscountPercentage());
        coupon.setActive(couponCreateDto.isActive());
        couponRepository.save(coupon);
    }

    @Override
    public CouponUpdateDto getUpdateCoupon(Long id) {
        Coupon coupon=couponRepository.findById(id).orElseThrow();
        CouponUpdateDto couponUpdateDto=modelMapper.map(coupon,CouponUpdateDto.class);
        return couponUpdateDto;
    }

    @Override
    public void updateCoupon(Long id, CouponUpdateDto couponUpdateDto) {
        Coupon coupon=couponRepository.findById(id).orElseThrow();
        coupon.setCode(couponUpdateDto.getCode());
        coupon.setDiscountPercentage(couponUpdateDto.getDiscountPercentage());
        coupon.setActive(couponUpdateDto.isActive());
        couponRepository.save(coupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        Coupon coupon=couponRepository.findById(id).orElseThrow();
        couponRepository.delete(coupon);
    }
}
