package itbrains.az.edu.vegetables.controllers.dashboard;

import itbrains.az.edu.vegetables.dtos.category.CategoryCreateDto;
import itbrains.az.edu.vegetables.dtos.category.CategoryDashboardDto;
import itbrains.az.edu.vegetables.dtos.category.CategoryUpdateDto;
import itbrains.az.edu.vegetables.dtos.review.ReviewDashboardDto;
import itbrains.az.edu.vegetables.dtos.review.ReviewUpdateDto;
import itbrains.az.edu.vegetables.services.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/admin/review")
    public String index(Model model){
        List<ReviewDashboardDto> reviewDashboardDtoList=reviewService.getDashboardReviews();
        model.addAttribute("reviews",reviewDashboardDtoList);
        return "/dashboard/review/index.html";
    }
    @GetMapping("/admin/review/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        ReviewUpdateDto reviewUpdateDto=reviewService.getUpdateReview(id);
        model.addAttribute("review",reviewUpdateDto);
        return "/dashboard/review/update.html";
    }
    @PostMapping("/admin/review/edit/{id}")
    public String edit(@PathVariable Long id, ReviewUpdateDto reviewUpdateDto){
        reviewService.updateReview(id,reviewUpdateDto);
        return "redirect:/admin/review";
    }
    @GetMapping("/admin/review/delete/{id}")
    public String delete(@PathVariable("id") Long id,Model model){
        model.addAttribute("id", id);
        return  "/dashboard/review/delete.html";
    }
    @PostMapping("/admin/review/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
        return "redirect:/admin/review";
    }
}
