package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.dtos.*;
import itbrains.az.edu.vegetables.models.*;
import itbrains.az.edu.vegetables.services.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SliderService sliderService;
    private final FeaturService featurService;
    private final FeaturElementService featurElementService;
    private final BannerService bannerService;
    private final TestimonialService testimonialService;
    private final SubCategoryService subCategoryService;
    private final CartService cartService;
    private final OrderService orderService;
    public HomeController(ProductService productService, CategoryService categoryService, ModelMapper modelMapper, SliderService sliderService, FeaturService featurService, FeaturElementService featurElementService, BannerService bannerService, TestimonialService testimonialService, SubCategoryService subCategoryService, CartService cartService, OrderService orderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.sliderService = sliderService;
        this.featurService = featurService;
        this.featurElementService = featurElementService;
        this.bannerService = bannerService;
        this.testimonialService = testimonialService;
        this.subCategoryService = subCategoryService;
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
        List<SubCategoryDto> subCategories = subCategoryService.getAllSubCategoriesWithProductCount();
        model.addAttribute("products", paginatedProducts);
        model.addAttribute("subCategories", subCategories);
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
    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal, HttpSession session) {
        String username = principal.getName();
        List<Cart> cartItems = cartService.getCartItemsByUsername(username);
        double subtotal = cartService.calculateSubtotal(cartItems);

        CouponDto appliedCoupon = (CouponDto) session.getAttribute("appliedCoupon");
        double discountAmount = 0.0;
        if (appliedCoupon != null && appliedCoupon.isActive()) {
            discountAmount = subtotal * (appliedCoupon.getDiscountPercentage() / 100);
        }
        double shipping = 3.0;
        double total = subtotal - discountAmount + shipping;
        model.addAttribute("total", total);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("orderDto", new OrderDto());          // ✨
        model.addAttribute("countries", Locale.getISOCountries());
        return "checkout.html";

    }
    @PostMapping("/checkout")
    public String placeOrder(@Valid @ModelAttribute("orderDto") OrderDto dto,
                             BindingResult result,
                             Principal principal,
                             RedirectAttributes ra) {

        if (result.hasErrors()) {
            return "checkout";
        }

        orderService.createOrder(dto, principal.getName()); // mövcud servis
        ra.addFlashAttribute("success", "Sifarişiniz qəbul olundu!");
        return "redirect:/checkout";
    }
    @PostMapping("/updates")
    public String updateQuantity(@RequestParam Long productId,
                                 @RequestParam(required = false) Integer quantity,
                                 @RequestParam String action,
                                 Principal principal) {
        String username = principal.getName();

        if (action.equals("increase")) {
            cartService.increaseQuantity(username, productId);
        } else if (action.equals("decrease")) {
            cartService.decreaseQuantity(username, productId);
        }

        return "redirect:/checkout";
    }


    @PostMapping("/deletes")
    public String deleteItem(@RequestParam Long productId, Principal principal) {
        String username = principal.getName();
        cartService.deleteItem(username, productId);
        return "redirect:/checkout";
    }

}
