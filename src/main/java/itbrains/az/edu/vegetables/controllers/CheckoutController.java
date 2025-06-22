package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.dtos.coupon.CouponDto;
import itbrains.az.edu.vegetables.dtos.order.OrderDto;
import itbrains.az.edu.vegetables.models.Cart;
import itbrains.az.edu.vegetables.services.CartService;
import itbrains.az.edu.vegetables.services.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Controller
public class CheckoutController {
    private final CartService cartService;
    private final OrderService orderService;

    public CheckoutController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal, HttpSession session) {
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
        model.addAttribute("total", total);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("orderDto", new OrderDto());          // ✨
        model.addAttribute("countries", Locale.getISOCountries());
        return "checkout.html";

    }
    @PostMapping("/checkout")
    public String placeOrder(@Valid @ModelAttribute("orderDto") OrderDto dto,
                             BindingResult result,
                             Principal principal,
                             RedirectAttributes ra) {

        if (result.hasErrors()) {
            return "checkout";
        }

        orderService.createOrder(dto, principal.getName()); // mövcud servis
        ra.addFlashAttribute("success", "Sifarişiniz qəbul olundu!");
        return "redirect:/checkout";
    }
    @PostMapping("/updates")
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

        return "redirect:/checkout";
    }


    @PostMapping("/deletes")
    public String deleteItem(@RequestParam Long productId, Principal principal) {
        String username = principal.getName();
        cartService.deleteItem(username, productId);
        return "redirect:/checkout";
    }

}
