package itbrains.az.edu.vegetables.controllers.dashboard;

import itbrains.az.edu.vegetables.dtos.category.CategoryDashboardDto;
import itbrains.az.edu.vegetables.dtos.product.ProductCreateDto;
import itbrains.az.edu.vegetables.dtos.product.ProductDashboardDto;
import itbrains.az.edu.vegetables.dtos.product.ProductUpdateDto;
import itbrains.az.edu.vegetables.services.CategoryService;
import itbrains.az.edu.vegetables.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductsController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductsController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/product")

    public String getAll(Model model) {
        List<ProductDashboardDto> allProduct = productService.getProductAll();
        model.addAttribute("products", allProduct);
        return "/dashboard/product/index";
    }
    @GetMapping("/admin/product/create")
    public String create(Model model) {
        model.addAttribute("product", new ProductCreateDto());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/dashboard/product/create";
    }


    @PostMapping("/admin/product/create")
    public String create(@ModelAttribute("product") ProductCreateDto productCreateDto) {
        productService.createProduct(productCreateDto);
        return "redirect:/admin/product";

    }
    @GetMapping("/admin/product/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        ProductUpdateDto productUpdateDto = productService.getUpdateProduct(id);
        List<CategoryDashboardDto> categoryDtoList = categoryService.getDashboardCategories();
        model.addAttribute("categories", categoryDtoList);

        model.addAttribute("product", productUpdateDto);

        return "/dashboard/product/update";
    }

    @PostMapping("/admin/product/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute("product") ProductUpdateDto productUpdateDto) {
        productService.updateProduct(id, productUpdateDto);
        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        return  "/dashboard/product/delete.html";
    }
    @PostMapping("/admin/product/delete/{id}")
    public String mdelete(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/product";
    }
}
