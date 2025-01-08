//package group7.entity;
//
//import group7.validation.annotation.ValidBirthday;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Deprecated
//@Entity
//@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
//@NoArgsConstructor
//@Data
//@NamedEntityGraph(
//        name = "User.ordersAddressesRoles",
//        attributeNodes = {
//                @NamedAttributeNode("orders"),
//                @NamedAttributeNode("billingAddresses"),
//                @NamedAttributeNode("deliveryAddresses"),
//                @NamedAttributeNode("roles")
//        }
//)
//public class User_ {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_id", nullable = false)
//    private Long id;
//
//    @NotNull(message = "username cannot be null")
//    @NotEmpty(message = "username cannot be empty")
//    @NotBlank(message = "username is required.")
//    @Size(min = 4, max = 15, message = "name must be between 4 and 15 characters")
//    @Column(unique = true, name = "username", nullable = false)
//    private String username;
//
//    @NotNull(message = "password cannot be null")
//    @NotEmpty(message = "password cannot be empty")
//    @NotBlank(message = "password is required.")
//    @Pattern(regexp = ".*[0-9].*")
//    @Size(min = 8, max = 15, message = "password must be between 8 and 15 characters")
//    @Column(name = "password", nullable = false)
//    private String password;
//
//    @ValidBirthday(message = "Birthday must be after 01.01.1900 and before or on today's date")
//    @Past(message = "Birthday must be in the past")
//    @Column(name = "birthday", nullable = false)
//    private LocalDate birthday;
//
//    // by default FetchType LAZY, so implement graph entity for this to avoid future queries
//    @ManyToMany
//    @JoinTable(
//            name = "user_billing_addresses",
//            joinColumns = @JoinColumn(name = "user_id", nullable = false),
//            inverseJoinColumns = @JoinColumn(name = "address_id", nullable = true)
//    )
//    private List<Address> billingAddresses = new ArrayList<>(); //to avoid null pointer exception
//
//    // by default FetchType LAZY, so implement graph entity for this to avoid future queries
//    @ManyToMany
//    @JoinTable(
//            name = "user_delivery_addresses",
//            joinColumns = @JoinColumn(name = "user_id", nullable = false),
//            inverseJoinColumns = @JoinColumn(name = "address_id", nullable = true)
//    )
//    private List<Address> deliveryAddresses = new ArrayList<>();
//
//    // by default FetchType LAZY, so implement graph entity for this to avoid future queries
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Order> orders = new ArrayList<>();
//
//    //role relationship, By default Lazy, so use GraphEntity Solution.
//    //referencedColumnName, if not specified JPA will consider the primary key of the corresponding table as the default
//    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY)
//    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"), inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
//    private Set<Role> roles = new HashSet<>();
//
//    public User_(
//            String username,
//            String password,
//            LocalDate birthday,
//            List<Address> billingAddresses,
//            List<Address> deliveryAddresses,
//            List<Order> orders,
//            Set<Role> roles
//    ) {
//        this.username = username;
//        this.password = password;
//        this.birthday = birthday;
//        if (billingAddresses != null) this.billingAddresses = billingAddresses;
//        if (deliveryAddresses != null) this.deliveryAddresses = deliveryAddresses;
//        if (orders != null) this.orders = orders;
//        if (roles != null) this.roles = roles;
//    }
//
//    public void addBillingAddresses(Address address) {
//        if (address == null) {
//            throw new IllegalArgumentException("address cannot be null.");
//        }
//        this.billingAddresses.add(address);
//    }
//
//    public void removeBillingAddresses(Address address){
//        this.billingAddresses.remove(address);
//        //Address side bidirectional relationship is commented out, no bidirectional is used for now
//        //address.removeUsersBilling(this); // Break the bidirectional relationship
//    }
//
//    public void addDeliveryAddress(Address address) {
//        if(address != null) this.deliveryAddresses.add(address);
//    }
//
//    public void removeDeliveryAddress(Address address){
//        this.deliveryAddresses.remove(address);
//        //Address's side bidirectional relationship is commented out, no bidirectional is used for now
//        //address.removeUsersDelivery(this); // Break the bidirectional relationship
//    }
//}
