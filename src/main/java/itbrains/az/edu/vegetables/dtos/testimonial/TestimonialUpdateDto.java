package itbrains.az.edu.vegetables.dtos.testimonial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialUpdateDto {
    private Long id;
    private String description;
    private String imageUrl;
    private String clientName;
    private String profession;
    private double rate;
}
