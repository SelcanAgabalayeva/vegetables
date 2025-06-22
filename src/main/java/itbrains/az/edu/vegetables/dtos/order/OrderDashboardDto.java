package itbrains.az.edu.vegetables.dtos.order;

import itbrains.az.edu.vegetables.models.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDashboardDto {
    private Long orderId;
    private String firstName;
    private String lastName;
    private String addressLine;
    private String city;
    private String country;
    private String postcode;
    private String mobile;
    private OrderStatus status;
    private String email;
    private PaymentMethod paymentMethod;
    private List<CartItemDto> items;
}
