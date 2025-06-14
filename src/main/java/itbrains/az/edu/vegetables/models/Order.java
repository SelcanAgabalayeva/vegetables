package itbrains.az.edu.vegetables.models;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.processing.Pattern;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String firstName;
    @NotBlank private String lastName;



    @NotBlank private String addressLine;
    @NotBlank
    private String city;
    @NotBlank private String country;
    @NotBlank private String postcode;

    @NotBlank  private String mobile;
    @Email
    @NotBlank private String email;

    private Boolean createAccount  = false;
    private Boolean shipToDifferent = false;

    private String orderNotes;

    @NotNull
    private PaymentMethod paymentMethod;
}
