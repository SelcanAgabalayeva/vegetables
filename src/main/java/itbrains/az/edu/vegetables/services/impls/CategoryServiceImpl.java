package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.models.Category;
import itbrains.az.edu.vegetables.repositories.CategoryRepository;
import itbrains.az.edu.vegetables.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }



    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}
