package itbrains.az.edu.vegetables.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(length = 20000)
    private String longDescription;
    private String origin;
    private String imageUrl;
    private double price;
    private String minWeight;
    private String weight;
    private double rate;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
