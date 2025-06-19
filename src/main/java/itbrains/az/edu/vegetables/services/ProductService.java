package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.BestSellerProductDto;
import itbrains.az.edu.vegetables.dtos.product.ProductDto;
import itbrains.az.edu.vegetables.dtos.ShopDetailDto;
import itbrains.az.edu.vegetables.dtos.product.ProductCreateDto;
import itbrains.az.edu.vegetables.dtos.product.ProductDashboardDto;
import itbrains.az.edu.vegetables.dtos.product.ProductUpdateDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getallProducts();

    List<BestSellerProductDto> getAllBestProduct();


    List<ProductDto> getProductsByCategoryId(Long categoryId);

    ShopDetailDto getShopDetail(Long id);

    ProductDto getProductById(Long id);


    List<ProductDto> getTopInCartProducts(int limit);

    List<ProductDashboardDto> getProductAll();

    void createProduct(ProductCreateDto productCreateDto);

    ProductUpdateDto getUpdateProduct(Long id);

    void updateProduct(Long id, ProductUpdateDto productUpdateDto);

    void deleteProduct(Long id);
}
