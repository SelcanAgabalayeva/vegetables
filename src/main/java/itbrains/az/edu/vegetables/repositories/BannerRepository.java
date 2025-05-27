package itbrains.az.edu.vegetables.repositories;

import itbrains.az.edu.vegetables.models.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner,Long> {
}
