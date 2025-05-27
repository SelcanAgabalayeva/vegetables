package itbrains.az.edu.vegetables.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "subcategories")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g. Apple, Pear, Strawberry, Grape

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "subCategory")
    private List<Product> products;
}
