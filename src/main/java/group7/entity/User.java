package group7.entity;

import group7.validation.annotation.ValidBirthday;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(unique = true, name = "username", nullable = false)
    private String username;

    @NotNull
    @NotEmpty
    @Pattern(regexp = ".*[0-9].*")
    @Column(name = "password", nullable = false)
    private String password;

    @ValidBirthday
    @Past(message = "Birthday must be in the past")
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotEmpty(message = "Billing addresses cannot be empty")
    private List<Address> billingAddresses;

    @OneToMany
    private List<Address> deliveryAddresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    public User(
            String username,
            String password,
            LocalDate birthday,
            List<Address> billingAddresses,
            List<Address> deliveryAddresses,
            List<Order> orders
    ) {
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.billingAddresses = billingAddresses;
        this.deliveryAddresses = deliveryAddresses;
        this.orders = orders;
    }
}
