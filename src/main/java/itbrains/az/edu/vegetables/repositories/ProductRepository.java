package itbrains.az.edu.vegetables.repositories;

import itbrains.az.edu.vegetables.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
