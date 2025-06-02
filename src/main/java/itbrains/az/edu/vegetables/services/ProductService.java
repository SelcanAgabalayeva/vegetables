package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.BestSellerProductDto;
import itbrains.az.edu.vegetables.dtos.ProductDto;
import itbrains.az.edu.vegetables.dtos.ShopDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductService {
    List<ProductDto> getallProducts();

    List<BestSellerProductDto> getAllBestProduct();


    List<ProductDto> getProductsByCategoryId(Long categoryId);

    ShopDetailDto getShopDetail(Long id);

    ProductDto getProductById(Long id);

}
