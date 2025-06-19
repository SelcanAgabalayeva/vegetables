package itbrains.az.edu.vegetables.repositories;

import itbrains.az.edu.vegetables.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findTopByOrderByIdDesc();

}
