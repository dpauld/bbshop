package group7.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@NoArgsConstructor // Write a constructor that has no arguments automatically
@AllArgsConstructor // Write a constructor that has all arguments automatically
@Data // Write all getters and setters automatically
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @Column(name = "street", nullable = false)
    private String street;

    @NotEmpty
    @Column(name = "number", nullable = false)
    private String number;

    @NotEmpty
    @Size(max = 5, message = "Postal code must shorter than 5 characters") // Length constraint
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

}
