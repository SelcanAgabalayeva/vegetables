package itbrains.az.edu.vegetables.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long id;
    private Long productId;
    private String name;
    private String imageUrl;
    private int quantity;
    private double price;
    public double getTotal() {
        return quantity * price;
    }
}
