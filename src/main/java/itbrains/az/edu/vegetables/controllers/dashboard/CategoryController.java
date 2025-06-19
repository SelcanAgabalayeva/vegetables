package itbrains.az.edu.vegetables.controllers.dashboard;

import itbrains.az.edu.vegetables.dtos.category.CategoryCreateDto;
import itbrains.az.edu.vegetables.dtos.category.CategoryDashboardDto;
import itbrains.az.edu.vegetables.dtos.category.CategoryUpdateDto;
import itbrains.az.edu.vegetables.services.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/category")
    public String index(Model model){
        List<CategoryDashboardDto> categoryDashboardDtoList=categoryService.getDashboardCategories();
        model.addAttribute("categories",categoryDashboardDtoList);
        return "/dashboard/category/index.html";
    }
    @GetMapping("/admin/category/create")
    public String create(){

        return "/dashboard/category/create.html";
    }

    @PostMapping("/admin/category/create")
    public String create(CategoryCreateDto categoryCreateDto){
        categoryService.createCategory(categoryCreateDto);
        return "redirect:/admin/category";
    }
    @GetMapping("/admin/category/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        CategoryUpdateDto categoryUpdateDto=categoryService.getUpdateCategory(id);
        model.addAttribute("category",categoryUpdateDto);
        return "/dashboard/category/update.html";
    }
    @PostMapping("/admin/category/edit/{id}")
    public String edit(@PathVariable Long id, CategoryUpdateDto categoryUpdateDto){
        categoryService.updateCategory(id,categoryUpdateDto);
        return "redirect:/admin/category";
    }
    @GetMapping("/admin/category/delete/{id}")
    public String delete(@PathVariable("id") Long id,Model model){
        model.addAttribute("id", id);
        return  "/dashboard/category/delete.html";
    }
    @PostMapping("/admin/category/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }
}
