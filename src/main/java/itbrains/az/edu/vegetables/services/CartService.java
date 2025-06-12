package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.models.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getCartItemsByUsername(String username);

    double calculateSubtotal(List<Cart> cartItems);

    void increaseQuantity(String username, Long productId);

    void decreaseQuantity(String username, Long productId);

    void deleteItem(String username, Long productId);

    void addToCart(String username, Long productId);
}
