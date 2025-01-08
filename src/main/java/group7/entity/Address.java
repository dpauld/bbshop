package group7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "street can not be empty.")
    @NotNull(message = "street can not be null.")
    @NotBlank(message = "street address is required.")
    @Column(name = "street", nullable = false)
    private String street;

    @NotEmpty(message = "number can not be empty.")
    @NotNull(message = "number can not be null.")
    @NotBlank(message = "number is required.")
    @Column(name = "number", nullable = false)
    private String number;

    @NotEmpty
    @NotNull
    @NotBlank(message = "postal code is required.")
    @Size(min = 5, max = 5)
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    //Not necessary to store user associated with each user.
//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "billingAddresses", cascade = {MERGE, PERSIST})
//    private List<User> usersBilling;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "deliveryAddresses", cascade = {MERGE, PERSIST})
//    private List<User> usersDelivery;

//    public void addUsersBilling(User user) {
//        if (user != null) {
//            this.usersBilling.add(user);
//        }
//    }
//    public void removeUsersBilling(User user) {
//        if (user != null) {
//            this.usersBilling.remove(user);
//        }
//    }

//    public void addUsersDelivery(User user) {
//        if (user != null) {
//            this.usersDelivery.add(user);
//        }
//    }
//    public void removeUsersDelivery(User user) {
//        if (user != null) {
//            this.usersDelivery.remove(user);
//        }
//    }

    public Address(String street, String number, String postalCode) {
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
    }
}