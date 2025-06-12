package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.models.Product;
import itbrains.az.edu.vegetables.models.Review;
import itbrains.az.edu.vegetables.repositories.ProductRepository;
import itbrains.az.edu.vegetables.repositories.ReviewRepository;
import itbrains.az.edu.vegetables.services.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ShopDetailController {
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ShopDetailController(ReviewService reviewService, ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }
    @GetMapping("/shop-detail")
    public String shopDetails(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        List<Review> reviews = reviewRepository.findByProductId(id); // <-- Bura mühümdür

        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        return "shop-detail";
    }
    @PostMapping("/shop-detail")
    public String submitReview(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String comment,
                               @RequestParam int stars,
                               @RequestParam Long productId,
                               Model model) {

        Review review = new Review();
        review.setName(name);
        review.setComment(comment);
        review.setStars(stars);
        review.setDate(LocalDate.now());
        review.setAvatarUrl("https://www.freeiconspng.com/thumbs/profile-icon-png/profile-icon-9.png"); // Default avatar
        review.setProductId(productId);
        reviewRepository.save(review);

        return "redirect:/shop/detail/" + productId;
    }




}
