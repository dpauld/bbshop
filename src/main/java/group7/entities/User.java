package group7.entities;

import group7.validation.annotation.ValidBirthday;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    //Validation commented for now, cause not sure weather we implement dto for user or not.
//    @Size(min=5, message = "username must be 5 charachters long.")
//    @Size(max=15, message = "username can not exceed 15 charachter.")
//    @NotBlank(message = "username cannot be blank") //ensure user does not put only blanks " ", null, ""
    @Column(unique = true, name = "username", nullable = false)
    private String username;

//    @Pattern(regexp = ".*[0-9].*", message = "password must contain one digit")
//    @Size(min=5, message = "password must be 5 charachters long.")git checkout -b new-branch-name
//    @Size(max=15, message = "password can not exceed 15 charachter.")
//    @Pattern(regexp = ".*[0-9].*", message = "password must contain one digit")
    @Column(name = "password", nullable = false)
    private String password;

    @Past(message = "Birthday must be in the past")
    @ValidBirthday // Custom validation to check the date is after 1.1.1900
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    //If we don't have the user, no need to have their corresponding address, so CascadeType.ALL
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //remove mappedBy to make it unidirectional
    private List<Address> addresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}
