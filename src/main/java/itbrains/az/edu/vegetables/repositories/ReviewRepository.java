package itbrains.az.edu.vegetables.repositories;

import itbrains.az.edu.vegetables.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);

}
