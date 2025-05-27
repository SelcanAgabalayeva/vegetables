package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.BestSellerProductDto;
import itbrains.az.edu.vegetables.dtos.CategoryDto;
import itbrains.az.edu.vegetables.dtos.ProductDto;
import itbrains.az.edu.vegetables.models.Product;
import itbrains.az.edu.vegetables.repositories.CategoryRepository;
import itbrains.az.edu.vegetables.repositories.ProductRepository;
import itbrains.az.edu.vegetables.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductDto> getallProducts() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getImageUrl(),
                        product.getPrice(),
                        product.getRate(),


                        new CategoryDto( // Category obyektindən CategoryDto yarat
                                product.getCategory().getId(),
                                product.getCategory().getName()


                        )
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<BestSellerProductDto> getAllBestProduct() {
        List<Product> allProducts = productRepository.findAll();

        return allProducts.stream()
                .filter(product -> product.getRate() > 4.0)
                .map(product -> {
                    BestSellerProductDto dto = new BestSellerProductDto();
                    dto.setId(product.getId());
                    dto.setName(product.getName());
                    dto.setDescription(product.getDescription());
                    dto.setImageUrl(product.getImageUrl());
                    dto.setPrice(product.getPrice());
                    dto.setRate(product.getRate()); // Əgər Dto-da rate String-dirsə

                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setId(product.getCategory().getId());
                    categoryDto.setName(product.getCategory().getName());

                    dto.setCategory(categoryDto);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
