package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.BestSellerProductDto;
import itbrains.az.edu.vegetables.dtos.CategoryDto;
import itbrains.az.edu.vegetables.dtos.ProductDto;
import itbrains.az.edu.vegetables.dtos.ShopDetailDto;
import itbrains.az.edu.vegetables.models.Product;
import itbrains.az.edu.vegetables.repositories.CategoryRepository;
import itbrains.az.edu.vegetables.repositories.ProductRepository;
import itbrains.az.edu.vegetables.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
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


                        new CategoryDto(
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
                    dto.setRate(product.getRate());

                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setId(product.getCategory().getId());
                    categoryDto.setName(product.getCategory().getName());

                    dto.setCategory(categoryDto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getImageUrl(),
                        product.getPrice(),
                        product.getRate(),
                        new CategoryDto(
                                product.getCategory().getId(),
                                product.getCategory().getName()
                        )
                )).collect(Collectors.toList());
    }

    @Override
    public ShopDetailDto getShopDetail(Long id) {
        Product product=productRepository.findById(id).orElseThrow();
        ShopDetailDto shopDetailDto=modelMapper.map(product,ShopDetailDto.class);
        return shopDetailDto;
    }
    private ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        if (product.getSubCategory() != null && product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            dto.setCategory(categoryDto);
        } else {
            dto.setCategory(null);
        }
        return dto;
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return mapToDto(product);
    }




}
