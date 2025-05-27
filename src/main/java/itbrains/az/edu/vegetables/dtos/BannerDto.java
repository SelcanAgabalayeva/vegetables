package itbrains.az.edu.vegetables.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerDto {
    private Long id;
    private String title;
    private String subtitle;
    private String description;
    private String imageUrl;
    private String price;
    private Integer number;
}
