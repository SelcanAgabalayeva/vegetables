package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.SubCategoryDto;
import itbrains.az.edu.vegetables.repositories.SubCategoryRepository;
import itbrains.az.edu.vegetables.services.SubCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public List<SubCategoryDto> getAllSubCategoriesWithProductCount() {
        return subCategoryRepository.findAllWithProductCount();
    }

}
