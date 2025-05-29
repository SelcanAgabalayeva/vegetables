package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.dtos.ProductDto;
import itbrains.az.edu.vegetables.dtos.ShopDetailDto;
import itbrains.az.edu.vegetables.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Controller
public class ShopController {
    private final ProductService productService;

    public ShopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/shop/detail/{id}")
    public String products(@PathVariable Long id, Model model) {
        ShopDetailDto shopDetailDto=productService.getShopDetail(id);

        model.addAttribute("product",shopDetailDto);
        return "shop-detail.html";
    }
}
