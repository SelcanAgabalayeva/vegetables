package itbrains.az.edu.vegetables.controllers.dashboard;

import itbrains.az.edu.vegetables.dtos.category.CategoryCreateDto;
import itbrains.az.edu.vegetables.dtos.category.CategoryDashboardDto;
import itbrains.az.edu.vegetables.dtos.category.CategoryUpdateDto;
import itbrains.az.edu.vegetables.dtos.coupon.CouponCreateDto;
import itbrains.az.edu.vegetables.dtos.coupon.CouponDashboardDto;
import itbrains.az.edu.vegetables.dtos.coupon.CouponUpdateDto;
import itbrains.az.edu.vegetables.services.CouponService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/admin/coupon")
    public String index(Model model){
        List<CouponDashboardDto> couponDashboardDtoList=couponService.getDashboardCoupon();
        model.addAttribute("coupons",couponDashboardDtoList);
        return "/dashboard/coupon/index.html";
    }
    @GetMapping("/admin/coupon/create")
    public String create(){

        return "/dashboard/coupon/create.html";
    }

    @PostMapping("/admin/coupon/create")
    public String create(CouponCreateDto couponCreateDto){
        couponService.createCoupon(couponCreateDto);
        return "redirect:/admin/coupon";
    }
    @GetMapping("/admin/coupon/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        CouponUpdateDto couponUpdateDto=couponService.getUpdateCoupon(id);
        model.addAttribute("coupon",couponUpdateDto);
        return "/dashboard/coupon/update.html";
    }
    @PostMapping("/admin/coupon/edit/{id}")
    public String edit(@PathVariable Long id, CouponUpdateDto couponUpdateDto){
        couponService.updateCoupon(id,couponUpdateDto);
        return "redirect:/admin/coupon";
    }
    @GetMapping("/admin/coupon/delete/{id}")
    public String delete(@PathVariable("id") Long id,Model model){
        model.addAttribute("id", id);
        return  "/dashboard/coupon/delete.html";
    }
    @PostMapping("/admin/coupon/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        couponService.deleteCoupon(id);
        return "redirect:/admin/coupon";
    }
}
