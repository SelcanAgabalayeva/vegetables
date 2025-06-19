package itbrains.az.edu.vegetables.dtos;

import itbrains.az.edu.vegetables.dtos.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestSellerProductDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private Double rate;
    private CategoryDto category;
}
