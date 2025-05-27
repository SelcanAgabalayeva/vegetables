package itbrains.az.edu.vegetables.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeaturElementDto {
    private Long id;

    private String title;
    private String discount;
    private String imageUrl;
}
