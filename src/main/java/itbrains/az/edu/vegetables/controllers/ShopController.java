package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.dtos.product.ProductDto;
import itbrains.az.edu.vegetables.dtos.ShopDetailDto;
import itbrains.az.edu.vegetables.models.Review;
import itbrains.az.edu.vegetables.repositories.ReviewRepository;
import itbrains.az.edu.vegetables.services.CategoryService;
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
    private final ReviewRepository reviewRepository;
    private final CategoryService categoryService;
    public ShopController(ProductService productService, ReviewRepository reviewRepository, CategoryService categoryService) {
        this.productService = productService;
        this.reviewRepository = reviewRepository;
        this.categoryService = categoryService;
    }

    @GetMapping("/shop/detail/{id}")
    public String products(@PathVariable Long id, Model model) {
        ShopDetailDto shopDetailDto=productService.getShopDetail(id);
        ProductDto product = productService.getProductById(id);
        List<Review> reviews = reviewRepository.findByProductId(id);
        model.addAttribute("product", product);
        List<ProductDto> featuredProducts = productService.getTopInCartProducts(5);
        model.addAttribute("featuredProducts", featuredProducts);

        List<ProductDto> relatedProducts = productService.getProductsByCategoryId(product.getCategory().getId())
                .stream()
                .filter(p -> !p.getId().equals(product.getId()))
                .limit(8)
                .collect(Collectors.toList());

        model.addAttribute("relatedProducts", relatedProducts);
        model.addAttribute("product",shopDetailDto);
        model.addAttribute("reviews", reviews);

        return "shop-detail.html";
    }

}
