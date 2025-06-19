package itbrains.az.edu.vegetables.repositories;

import itbrains.az.edu.vegetables.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
