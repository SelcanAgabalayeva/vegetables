package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.BestSellerProductDto;
import itbrains.az.edu.vegetables.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getallProducts();

    List<BestSellerProductDto> getAllBestProduct();
}
