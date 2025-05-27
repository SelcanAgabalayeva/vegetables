package itbrains.az.edu.vegetables.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeaturDto {
    private Long id;
    private String title;
    private String subTitle;
    private String imageUrl;
}
