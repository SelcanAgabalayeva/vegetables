package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.dtos.ProductDto;
import itbrains.az.edu.vegetables.dtos.ShopDetailDto;
import itbrains.az.edu.vegetables.models.Product;
import itbrains.az.edu.vegetables.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ShopController {
    private final ProductService productService;

    public ShopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/shop/detail/{id}")
    public String products(@PathVariable Long id, Model model) {
        ShopDetailDto shopDetailDto=productService.getShopDetail(id);
        ProductDto product = productService.getProductById(id);
        model.addAttribute("product", product);

        // Related products by same category
        List<ProductDto> relatedProducts = productService.getProductsByCategoryId(product.getCategory().getId())
                .stream()
                .filter(p -> !p.getId().equals(product.getId())) // Eyni məhsul çıxarılır
                .limit(8) // Maksimum 8 related məhsul göstər
                .collect(Collectors.toList());

        model.addAttribute("relatedProducts", relatedProducts);
        model.addAttribute("product",shopDetailDto);
        return "shop-detail.html";
    }
}
