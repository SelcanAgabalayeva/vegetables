package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.dtos.*;
import itbrains.az.edu.vegetables.dtos.product.BestSellerProductDto;
import itbrains.az.edu.vegetables.dtos.product.ProductDto;
import itbrains.az.edu.vegetables.dtos.testimonial.TestimonialDto;
import itbrains.az.edu.vegetables.models.*;
import itbrains.az.edu.vegetables.repositories.CategoryRepository;
import itbrains.az.edu.vegetables.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private final CategoryRepository categoryRepository;

    private final CartService cartService;
    private final OrderService orderService;
    public HomeController(ProductService productService, CategoryService categoryService, ModelMapper modelMapper, SliderService sliderService, FeaturService featurService, FeaturElementService featurElementService, BannerService bannerService, TestimonialService testimonialService, CategoryRepository categoryRepository, CartService cartService, OrderService orderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.sliderService = sliderService;
        this.featurService = featurService;
        this.featurElementService = featurElementService;
        this.bannerService = bannerService;
        this.testimonialService = testimonialService;
        this.categoryRepository = categoryRepository;

        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductDto> allProducts = productService.getallProducts();
        List<Category> categories = categoryService.getAllCategories();
        List<SliderDto> sliders=sliderService.getAllSliders();
        List<FeaturDto> featurDtoList=featurService.getAllFeaturs();
        List<FeaturElementDto> featurElementDtoList=featurElementService.getAllFeaturElement();
        List<BestSellerProductDto> bestProducts = productService.getAllBestProduct();
        model.addAttribute("bestProduct", bestProducts);
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

        return "index.html";

    }
    @GetMapping("/contact")
    public String contact() {
        return "contact.html";

    }

    @GetMapping("/shop")
    public String shop(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "0") int catPage,
                       @RequestParam(required = false) Long categoryId,

                       Model model) {

        int productPageSize = 3;
        int categoryPageSize = 4;

        List<Category> allCategories = categoryService.getAllCategories();
        int totalCategories = allCategories.size();
        int totalCategoryPages = (int) Math.ceil((double) totalCategories / categoryPageSize);
        int catStart = catPage * categoryPageSize;
        int catEnd = Math.min(catStart + categoryPageSize, totalCategories);
        List<Category> paginatedCategories = allCategories.subList(catStart, catEnd);
        List<ProductDto> featuredProducts = productService.getTopInCartProducts(5);
        model.addAttribute("featuredProducts", featuredProducts);
        List<ProductDto> allProducts;
        if (categoryId != null) {
            allProducts = productService.getProductsByCategoryId(categoryId);
        } else {
            allProducts = productService.getallProducts();
        }
        int totalProducts = allProducts.size();
        int totalProductPages = (int) Math.ceil((double) totalProducts / productPageSize);
        int start = page * productPageSize;
        int end = Math.min(start + productPageSize, totalProducts);
        List<ProductDto> paginatedProducts = allProducts.subList(start, end);
        List<Category> categoriess = categoryRepository.findAllWithProducts();
        model.addAttribute("categoriess", categoriess);

        model.addAttribute("products", paginatedProducts);

        model.addAttribute("categories", paginatedCategories);

        model.addAttribute("selectedCategoryId", categoryId);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalProductPages);

        model.addAttribute("catPage", catPage);
        model.addAttribute("totalCategoryPages", totalCategoryPages);

        return "shop.html";

    }

    @GetMapping("/testimonial")
    public String testimonial(Model model) {
        List<TestimonialDto> testimonials = testimonialService.getAllTestimonial();
        model.addAttribute("testimonials",testimonials);
        return "testimonial.html";

    }
    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<ProductDto> results = productService.searchProductsByName(query);
        model.addAttribute("product", results);
        return "search.html";
    }


}
