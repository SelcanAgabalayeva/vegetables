package itbrains.az.edu.vegetables.dtos.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateDto {
    private Long id;
    private String name;
}
