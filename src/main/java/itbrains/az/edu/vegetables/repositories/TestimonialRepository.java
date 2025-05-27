package itbrains.az.edu.vegetables.repositories;

import itbrains.az.edu.vegetables.models.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestimonialRepository extends JpaRepository<Testimonial,Long> {
}
