package group7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @Column(name = "street", nullable = false)
    private String street;

    @NotEmpty
    @NotNull
    @Column(name = "number", nullable = false)
    private String number;

    @NotEmpty
    @NotNull
    @Size(min = 5, max = 5)
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Address(String street, String number, String postalCode, User user) {
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.user = user;
    }
}
