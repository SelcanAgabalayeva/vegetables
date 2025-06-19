package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.category.CategoryCreateDto;
import itbrains.az.edu.vegetables.dtos.category.CategoryDashboardDto;
import itbrains.az.edu.vegetables.dtos.category.CategoryUpdateDto;
import itbrains.az.edu.vegetables.models.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    List<CategoryDashboardDto> getDashboardCategories();

    void createCategory(CategoryCreateDto categoryCreateDto);

    CategoryUpdateDto getUpdateCategory(Long id);

    void updateCategory(Long id, CategoryUpdateDto categoryUpdateDto);

    void deleteCategory(Long id);
}
