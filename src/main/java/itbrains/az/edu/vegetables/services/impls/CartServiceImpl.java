package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.order.CartItemDto;
import itbrains.az.edu.vegetables.models.Cart;
import itbrains.az.edu.vegetables.models.Product;
import itbrains.az.edu.vegetables.models.User;
import itbrains.az.edu.vegetables.repositories.CartRepository;
import itbrains.az.edu.vegetables.repositories.ProductRepository;
import itbrains.az.edu.vegetables.repositories.UserRepository;
import itbrains.az.edu.vegetables.services.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
private final ProductRepository productRepository;
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Cart> getCartItemsByUsername(String username) {
        return cartRepository.findByUserUsername(username);
    }

    @Override
    public double calculateSubtotal(List<Cart> cartItems) {
        return cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    @Override
    public void increaseQuantity(String username, Long productId) {
        Cart item = cartRepository.findByUserUsernameAndProductId(username, productId);
        if (item != null) {
            item.setQuantity(item.getQuantity() + 1);
            cartRepository.save(item);
        }
    }

    @Override
    public void decreaseQuantity(String username, Long productId) {
        Cart item = cartRepository.findByUserUsernameAndProductId(username, productId);
        if (item != null && item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            cartRepository.save(item);
        }
    }

    @Override
    public void deleteItem(String username, Long productId) {
        Cart item = cartRepository.findByUserUsernameAndProductId(username, productId);
        if (item != null) {
            cartRepository.delete(item);
        }
    }

    @Override
    public void addToCart(String username, Long productId) {
        User user = userRepository.findByUsername(username);
        Product product = productRepository.findById(productId).orElseThrow();

        Cart existingCartItem = cartRepository.findByUserAndProduct(user, product);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
        } else {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setProduct(product);
            newCart.setQuantity(1);
            newCart.setName(product.getName());
            newCart.setImageUrl(product.getImageUrl());
            newCart.setPrice(product.getPrice());
            cartRepository.save(newCart);
            return;
        }

        cartRepository.save(existingCartItem);
    }

    @Override
    public List<CartItemDto> findItems(String name) {
        List<Cart> carts = cartRepository.findByUserUsername(name);


        return carts.stream().map(cart -> {
            CartItemDto dto = new CartItemDto();
            dto.setProductId(cart.getProduct().getId());
            dto.setName(cart.getProduct().getName());
            dto.setImageUrl(cart.getProduct().getImageUrl());
            dto.setQuantity(cart.getQuantity());
            dto.setPrice(cart.getProduct().getPrice());
            return dto;
        }).collect(Collectors.toList());
    }
}
