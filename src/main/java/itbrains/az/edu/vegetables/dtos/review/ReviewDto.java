package itbrains.az.edu.vegetables.dtos.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private String name;
    private String comment;
    private int stars;
    private String avatarUrl = "https://www.freeiconspng.com/thumbs/profile-icon-png/profile-icon-9.png"; // default avatar
    private LocalDate date = LocalDate.now();
}
