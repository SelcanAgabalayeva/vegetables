package itbrains.az.edu.vegetables.repositories;

import itbrains.az.edu.vegetables.dtos.SubCategoryDto;
import itbrains.az.edu.vegetables.models.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
//    @Query("SELECT new itbrains.az.edu.vegetables.dtos.SubCategoryDto(s.id, s.name, size(s.products)) FROM SubCategory s")
//    List<SubCategoryDto> findAllWithProductCount();
@Query("SELECT new itbrains.az.edu.vegetables.dtos.SubCategoryDto(s.id, s.name, COUNT(p)) " +
        "FROM SubCategory s LEFT JOIN s.products p " +
        "GROUP BY s.id, s.name")
List<SubCategoryDto> findAllWithProductCount();

}

