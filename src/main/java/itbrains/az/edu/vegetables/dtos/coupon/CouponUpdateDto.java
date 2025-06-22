package itbrains.az.edu.vegetables.dtos.coupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponUpdateDto {
    private Long id;
    private String code;
    private double discountPercentage;
    private boolean active;
}
