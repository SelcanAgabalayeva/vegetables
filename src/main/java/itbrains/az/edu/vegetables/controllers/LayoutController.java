package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.models.Order;
import itbrains.az.edu.vegetables.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class LayoutController {
    private final OrderService orderService;

    public LayoutController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ModelAttribute("latestOrder")
    public Order getLatestOrder() {
        return orderService.getLatestOrder(); // Ən son sifarişi qaytaran metod
    }
    @ModelAttribute("footerAboutText")
    public String getFooterAboutText() {
        return "Typesetting, remaining essentially unchanged. It was\n" +
                "                        popularised in the 1960s with the like Aldus PageMaker including of Lorem Ipsum.";
    }

}
