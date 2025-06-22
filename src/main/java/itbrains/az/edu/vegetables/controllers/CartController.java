package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.dtos.coupon.CouponDto;
import itbrains.az.edu.vegetables.models.Cart;
import itbrains.az.edu.vegetables.services.CartService;
import itbrains.az.edu.vegetables.services.CouponService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller

public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CouponService couponService;


    @GetMapping("/cart")
    public String viewCart(Model model, Principal principal, HttpSession session) {
        String username = principal.getName();
        List<Cart> cartItems = cartService.getCartItemsByUsername(username);

        double subtotal = cartService.calculateSubtotal(cartItems);

        CouponDto appliedCoupon = (CouponDto) session.getAttribute("appliedCoupon");
        double discountAmount = 0.0;
        if (appliedCoupon != null && appliedCoupon.isActive()) {
            discountAmount = subtotal * (appliedCoupon.getDiscountPercentage() / 100);
        }

        double shipping = 3.0;
        double total = subtotal - discountAmount + shipping;

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("discountAmount", discountAmount);
        model.addAttribute("shippingCost", shipping);
        model.addAttribute("total", total);
        model.addAttribute("shippingDestination", "Ukraine");

        if (appliedCoupon != null) {
            model.addAttribute("appliedCoupon", appliedCoupon);
        }

        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        cartService.addToCart(username, productId);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId,
                                 @RequestParam(required = false) Integer quantity,
                                 @RequestParam String action,
                                 Principal principal) {
        String username = principal.getName();

        if (action.equals("increase")) {
            cartService.increaseQuantity(username, productId);
        } else if (action.equals("decrease")) {
            cartService.decreaseQuantity(username, productId);
        }

        return "redirect:/cart";
    }


    @PostMapping("/delete")
    public String deleteItem(@RequestParam Long productId, Principal principal) {
        String username = principal.getName();
        cartService.deleteItem(username, productId);
        return "redirect:/cart";
    }

    @PostMapping("/apply-coupon")
    public String applyCoupon(@RequestParam String couponCode,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Optional<CouponDto> couponOpt = couponService.validateCoupon(couponCode);

        if (couponOpt.isPresent()) {
            session.setAttribute("appliedCoupon", couponOpt.get());
            redirectAttributes.addFlashAttribute("couponSuccess", "Coupon applied successfully!");
        } else {
            session.removeAttribute("appliedCoupon");
            redirectAttributes.addFlashAttribute("couponError", "Invalid or expired coupon.");
        }

        return "redirect:/cart";
    }

}
