package itbrains.az.edu.vegetables.controllers.dashboard;

import itbrains.az.edu.vegetables.dtos.category.CategoryCreateDto;
import itbrains.az.edu.vegetables.dtos.category.CategoryDashboardDto;
import itbrains.az.edu.vegetables.dtos.category.CategoryUpdateDto;
import itbrains.az.edu.vegetables.dtos.testimonial.TestimonialCreateDto;
import itbrains.az.edu.vegetables.dtos.testimonial.TestimonialDashboardDto;
import itbrains.az.edu.vegetables.dtos.testimonial.TestimonialUpdateDto;
import itbrains.az.edu.vegetables.services.TestimonialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TestimonialController {
    private final TestimonialService testimonialService;

    public TestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @GetMapping("/admin/testimonial")
    public String index(Model model){
        List<TestimonialDashboardDto> testimonialDashboardDtoList=testimonialService.getDashboardTestimonials();
        model.addAttribute("testimonials",testimonialDashboardDtoList);
        return "/dashboard/testimonial/index.html";
    }
    @GetMapping("/admin/testimonial/create")
    public String create(){

        return "/dashboard/testimonial/create.html";
    }

    @PostMapping("/admin/testimonial/create")
    public String create(TestimonialCreateDto testimonialCreateDto){
        testimonialService.createTestimonial(testimonialCreateDto);
        return "redirect:/admin/testimonial";
    }
    @GetMapping("/admin/testimonial/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        TestimonialUpdateDto testimonialUpdateDto=testimonialService.getUpdateTestimonial(id);
        model.addAttribute("testimonial",testimonialUpdateDto);
        return "/dashboard/testimonial/update.html";
    }
    @PostMapping("/admin/testimonial/edit/{id}")
    public String edit(@PathVariable Long id, TestimonialUpdateDto testimonialUpdateDto){
        testimonialService.updateTestimonial(id,testimonialUpdateDto);
        return "redirect:/admin/testimonial";
    }
    @GetMapping("/admin/testimonial/delete/{id}")
    public String delete(@PathVariable("id") Long id,Model model){
        model.addAttribute("id", id);
        return  "/dashboard/testimonial/delete.html";
    }
    @PostMapping("/admin/testimonial/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        testimonialService.deleteTestimonial(id);
        return "redirect:/admin/testimonial";
    }
}
