package itbrains.az.edu.vegetables.dtos.contact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDashboardDto {
    private Long id;
    private String name;
    private String email;
    private String message;
}
