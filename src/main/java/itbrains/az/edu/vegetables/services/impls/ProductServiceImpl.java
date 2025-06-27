package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.BestSellerProductDto;
import itbrains.az.edu.vegetables.dtos.category.CategoryDto;
import itbrains.az.edu.vegetables.dtos.product.ProductDto;
import itbrains.az.edu.vegetables.dtos.ShopDetailDto;
import itbrains.az.edu.vegetables.dtos.product.ProductCreateDto;
import itbrains.az.edu.vegetables.dtos.product.ProductDashboardDto;
import itbrains.az.edu.vegetables.dtos.product.ProductUpdateDto;
import itbrains.az.edu.vegetables.models.Category;
import itbrains.az.edu.vegetables.models.Product;
import itbrains.az.edu.vegetables.repositories.CartRepository;
import itbrains.az.edu.vegetables.repositories.CategoryRepository;
import itbrains.az.edu.vegetables.repositories.ProductRepository;
import itbrains.az.edu.vegetables.services.ProductService;
import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, CartRepository cartRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.cartRepository = cartRepository;
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
        return productRepository.findByRateGreaterThan(3.0).stream()
                .map(product -> {
                    BestSellerProductDto dto = modelMapper.map(product, BestSellerProductDto.class);

                    // Category-ni manuel set etmək
                    CategoryDto catDto = new CategoryDto();
                    catDto.setId(product.getCategory().getId());
                    catDto.setName(product.getCategory().getName());
                    dto.setCategory(catDto);

                    return dto;
                })
                .toList();
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
        dto.setRate(product.getRate());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        if (product.getCategory() != null) {
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

    @Override
    public List<ProductDto> getTopInCartProducts(int limit) {
        List<Product> products = cartRepository.findTopInCarts();
        return products.stream()
                .limit(limit > 0 ? limit : 8)  // default 8 məhsul göstər
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    @Override
    public List<ProductDashboardDto> getProductAll() {
        List<ProductDashboardDto> result = productRepository
                .findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(p -> modelMapper.map(p, ProductDashboardDto.class))
                .toList();
        return result;
    }

    @Override
    public void createProduct(ProductCreateDto productCreateDto) {
        Product product=new Product();
        product.setName(productCreateDto.getName());
        product.setDescription(productCreateDto.getDescription());
        product.setLongDescription(productCreateDto.getLongDescription());
        product.setOrigin(productCreateDto.getOrigin());
        product.setImageUrl(productCreateDto.getImageUrl());
        product.setPrice(productCreateDto.getPrice());
        product.setMinWeight(productCreateDto.getMinWeight());
        product.setWeight(productCreateDto.getWeight());
        product.setRate(productCreateDto.getRate());
        Category category = categoryRepository.findById(productCreateDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        productRepository.save(product);
    }

    @Override
    public ProductUpdateDto getUpdateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductUpdateDto dto = new ProductUpdateDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setLongDescription(product.getLongDescription());
        dto.setOrigin(product.getOrigin());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setMinWeight(product.getMinWeight());
        dto.setRate(product.getRate());
        dto.setWeight(product.getWeight());
        dto.setCategoryId(product.getCategory().getId());

        return dto;
    }

    @Override
    public void updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(productUpdateDto.getName());
        product.setDescription(productUpdateDto.getDescription());
        product.setLongDescription(productUpdateDto.getLongDescription());
        product.setOrigin(productUpdateDto.getOrigin());
        product.setImageUrl(productUpdateDto.getImageUrl());
        product.setPrice(productUpdateDto.getPrice());
        product.setMinWeight(productUpdateDto.getMinWeight());
        product.setWeight(productUpdateDto.getWeight());
        product.setRate(productUpdateDto.getRate());


        Category category = categoryRepository.findById(productUpdateDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product findProduct =productRepository.findById(id).orElseThrow();
        productRepository.delete(findProduct);
    }

    @Override
    public List<ProductDto> searchProductsByName(String query) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(query);
        return products.stream().map(this::mapToDto).collect(Collectors.toList());
    }


}
