package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.SubCategoryDto;

import java.util.List;

public interface SubCategoryService {
    List<SubCategoryDto> getAllSubCategoriesWithProductCount();
}
