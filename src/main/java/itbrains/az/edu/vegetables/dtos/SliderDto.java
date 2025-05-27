package itbrains.az.edu.vegetables.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SliderDto {
    private Long id;
    private String name;
    private String imageUrl;
    private String secondaryText;
    private String primaryText;
}
