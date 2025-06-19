package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.category.CategoryCreateDto;
import itbrains.az.edu.vegetables.dtos.category.CategoryDashboardDto;
import itbrains.az.edu.vegetables.dtos.category.CategoryUpdateDto;
import itbrains.az.edu.vegetables.models.Category;
import itbrains.az.edu.vegetables.repositories.CategoryRepository;
import itbrains.az.edu.vegetables.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }



    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryDashboardDto> getDashboardCategories() {
        return categoryRepository
                .findAll(Sort.by(Sort.Direction.ASC, "id"))   //
                .stream()
                .map(cat -> modelMapper.map(cat, CategoryDashboardDto.class))
                .toList();
    }

    @Override
    public void createCategory(CategoryCreateDto categoryCreateDto) {
        Category category = new Category();
        category.setName(categoryCreateDto.getName());
        categoryRepository.save(category);
    }

    @Override
    public CategoryUpdateDto getUpdateCategory(Long id) {
        Category category=categoryRepository.findById(id).orElseThrow();
        CategoryUpdateDto categoryUpdateDto=modelMapper.map(category,CategoryUpdateDto.class);
        return categoryUpdateDto;
    }

    @Override
    public void updateCategory(Long id, CategoryUpdateDto categoryUpdateDto) {
        Category category=categoryRepository.findById(id).orElseThrow();
        category.setName(categoryUpdateDto.getName());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category findCategory=categoryRepository.findById(id).orElseThrow();
        categoryRepository.delete(findCategory);
    }


}
