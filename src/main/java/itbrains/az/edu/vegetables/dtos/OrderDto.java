package itbrains.az.edu.vegetables.dtos;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import itbrains.az.edu.vegetables.models.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.processing.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String firstName;
    private String lastName;


     private String addressLine;
     private String city;
     private String country;
     private String postcode;

    private String mobile;

    private String email;
    private Boolean createAccount   = false;
    private Boolean shipToDifferent = false;
    private String  notes;
    private String orderNotes;

    @NotNull

    private PaymentMethod paymentMethod;
}
