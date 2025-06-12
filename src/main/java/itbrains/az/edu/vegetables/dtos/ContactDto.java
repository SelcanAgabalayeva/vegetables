package itbrains.az.edu.vegetables.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
    private String name;
    private String email;
    private String message;
}
