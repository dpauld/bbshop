package group7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@NoArgsConstructor
@Data
public class Address {
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
    @Size(min = 5, max = 5, message = "Postal code must be exactly 5 characters")
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    //need to have Entity Graph?
    @ManyToOne(fetch = FetchType.LAZY)
    //Automatically created by spring, not needed: @JoinColumn(name = "username"). Naming strategy, Lowercase(Entity name + Primary key) = user_id(if id is PK)
    private User user;

    public Address(String street, String number, String postalCode, User user) {
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.user = user;
    }
}
