package itbrains.az.edu.vegetables.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "subcategories")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Transient
    private int productCount;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "subCategory")
    private List<Product> products;

}
