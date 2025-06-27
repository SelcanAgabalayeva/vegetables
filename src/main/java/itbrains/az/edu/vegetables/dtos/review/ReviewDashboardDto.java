package itbrains.az.edu.vegetables.dtos.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDashboardDto {
    private Long id;
    private String avatarUrl;
    private LocalDate date;
    private String name;
    private int stars;
    private String comment;
    private Long productId;
}
