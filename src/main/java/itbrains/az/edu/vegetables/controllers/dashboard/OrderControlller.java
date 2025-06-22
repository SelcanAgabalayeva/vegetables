package itbrains.az.edu.vegetables.controllers.dashboard;

import itbrains.az.edu.vegetables.dtos.contact.ContactDashboardDto;
import itbrains.az.edu.vegetables.dtos.order.CartItemDto;
import itbrains.az.edu.vegetables.dtos.order.OrderDashboardDto;
import itbrains.az.edu.vegetables.dtos.order.OrderStatus;
import itbrains.az.edu.vegetables.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OrderControlller {
    private final OrderService orderService;

    public OrderControlller(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/orders")
    public String orders(Model model) {
        List<OrderDashboardDto> orders =orderService.findAllOrders();
        model.addAttribute("orders", orders);
        return "/dashboard/order/index";
    }
    @PostMapping("/admin/orders/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam OrderStatus status,
                               RedirectAttributes ra) {
        orderService.updateStatus(id, status);
        ra.addFlashAttribute("success", "Status yeniləndi");
        return "redirect:/admin/orders#order-" + id;
    }
    @GetMapping("/admin/orders/detail/{id}")
    public String detail(Model model, @PathVariable Long id) {
        List<CartItemDto> items = orderService.getOrderById(id);
        model.addAttribute("items", items);
        model.addAttribute("orderId", id);
        return "dashboard/order/detail";
    }

}
