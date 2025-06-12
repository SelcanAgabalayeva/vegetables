package itbrains.az.edu.vegetables.repositories;

import itbrains.az.edu.vegetables.models.Cart;
import itbrains.az.edu.vegetables.models.Product;
import itbrains.az.edu.vegetables.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByUser(User user);
    Cart findByUserAndProduct(User user, Product product);
    List<Cart> findAllByUserAndProductId(User user, Long productId);
    List<Cart> findByUserUsername(String username);
    Cart findByUserUsernameAndProductId(String username, Long productId);
}
