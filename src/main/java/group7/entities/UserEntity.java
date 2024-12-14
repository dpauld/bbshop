package group7.entities;

import group7.validation.annotation.ValidBirthday;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor // Write a constructor that has no arguments automatically
@AllArgsConstructor // Write a constructor that has all arguments automatically
@Data // Write all getters and setters automatically
public class UserEntity {

    @Id
    @NotEmpty
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Past(message = "Birthday must be in the past")
    @ValidBirthday // Custom validation to check the date is after 1.1.1900
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @OneToMany
    @JoinTable(
            name = "users-addresses",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "address_id")}
    )
    private List<AddressEntity> addresses;

    @OneToMany
    @JoinTable(
            name = "users-orders",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id")}
    )
    private List<OrderEntity> orders;

}
