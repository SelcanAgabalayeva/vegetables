package itbrains.az.edu.vegetables.dtos.product;

import itbrains.az.edu.vegetables.dtos.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopDetailDto {
    private Long id;
    private String name;
    private String description;
    private String longDescription;
    private String origin;
    private String imageUrl;
    private double price;
    private String minWeight;
    private String weight;
    private double rate;
    private CategoryDto category;

}
