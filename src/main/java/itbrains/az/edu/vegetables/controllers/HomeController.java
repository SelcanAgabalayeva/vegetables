package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.dtos.*;
import itbrains.az.edu.vegetables.models.Category;
import itbrains.az.edu.vegetables.models.Slider;
import itbrains.az.edu.vegetables.models.Testimonial;
import itbrains.az.edu.vegetables.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SliderService sliderService;
    private final FeaturService featurService;
    private final FeaturElementService featurElementService;
    private final BannerService bannerService;
    private final TestimonialService testimonialService;

    public HomeController(ProductService productService, CategoryService categoryService, ModelMapper modelMapper, SliderService sliderService, FeaturService featurService, FeaturElementService featurElementService, BannerService bannerService, TestimonialService testimonialService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.sliderService = sliderService;
        this.featurService = featurService;
        this.featurElementService = featurElementService;
        this.bannerService = bannerService;
        this.testimonialService = testimonialService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductDto> allProducts = productService.getallProducts();
        List<Category> categories = categoryService.getAllCategories();
        List<SliderDto> sliders=sliderService.getAllSliders();
        List<FeaturDto> featurDtoList=featurService.getAllFeaturs();
        List<FeaturElementDto> featurElementDtoList=featurElementService.getAllFeaturElement();
        List<BestSellerProductDto> bestSellerProductDtos=productService.getAllBestProduct();
        SliderDto sliderDto=sliderService.getSlider(1L);
        BannerDto bannerDto=bannerService.getBanner(1L);
        List<TestimonialDto> testimonials = testimonialService.getAllTestimonial();
        model.addAttribute("testimonials",testimonials);
        model.addAttribute("banners",bannerDto);
        model.addAttribute("products", allProducts);
        model.addAttribute("categories",categories);
        model.addAttribute("sliders",sliders);
        model.addAttribute("sliderSingle",sliderDto);
        model.addAttribute("featurs",featurDtoList);
        model.addAttribute("elements",featurElementDtoList);
        model.addAttribute("bestProduct",bestSellerProductDtos);
        return "index.html";

    }
    @GetMapping("/contact")
    public String contact() {
        return "contact.html";

    }

    @GetMapping("/shop")
    public String shop() {
        return "shop.html";

    }
    @GetMapping("/shop-detail")
    public String shopDetail() {
        return "shop-detail.html";

    }
    @GetMapping("/testimonial")
    public String testimonial(Model model) {
        List<TestimonialDto> testimonials = testimonialService.getAllTestimonial();
        model.addAttribute("testimonials",testimonials);
        return "testimonial.html";

    }
    @GetMapping("/checkout")
    public String checkout() {
        return "checkout.html";

    }
    @GetMapping("/cart")
    public String cart() {
        return "cart.html";

    }
}
