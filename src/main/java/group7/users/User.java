package group7.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import group7.entity.Address;
import group7.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@Entity
@JsonIgnoreProperties({"password", "fullName", "phoneNumber", "role", "authorities", "id", "enabled",
        "accountNonLocked", "credentialsNonExpired", "accountNonExpired"})
//@NamedEntityGraph(
//        name = "User.ordersBillingAndDeliveryAddresses",
//        attributeNodes = {
//                @NamedAttributeNode(value = "orders", subgraph = "Order.withItemsAndBeverage"),
//                @NamedAttributeNode("billingAddresses"),
//                @NamedAttributeNode("deliveryAddresses")
//        },
//        subgraphs = {
//                @NamedSubgraph(
//                        name = "Order.withItemsAndBeverage",
//                        attributeNodes = {
//                                @NamedAttributeNode(value = "orderItems", subgraph = "OrderItem.withBeverage")
//                        }
//                ),
//                @NamedSubgraph(
//                        name = "OrderItem.withBeverage",
//                        attributeNodes = {
//                                @NamedAttributeNode("beverage")
//                        }
//                )
//        }
//)
//@NamedEntityGraph(
//        name = "User.withOrdersAndAddresses",
//        attributeNodes = {
//                @NamedAttributeNode(value = "orders", subgraph = "Order.withOrderItems"),
//                @NamedAttributeNode("billingAddresses"),
//                @NamedAttributeNode("deliveryAddresses")
//        },
//        subgraphs = @NamedSubgraph(
//                name="Order.withOrderItems",
//                attributeNodes = {
//                        @NamedAttributeNode("orderItems")
//                })
//)
@NamedEntityGraph(
        name = "User.withOrdersAndAddresses",
        attributeNodes = {
                @NamedAttributeNode(value = "orders"),
                @NamedAttributeNode("billingAddresses"),
                @NamedAttributeNode("deliveryAddresses")
        }
)
@Setter
public class User implements UserDetails {          // interface from Spring Security

    private final String username;
    private final String password;
    private final String fullName;
    private final LocalDate birthday;
    private String role;

    // by default FetchType LAZY, so implement graph entity for this to avoid future queries
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_billing_addresses",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "address_id", nullable = true)
    )
    private Set<Address> billingAddresses = new HashSet<>(); //to avoid null pointer exception

    // by default FetchType LAZY, so implement graph entity for this to avoid future queries
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_delivery_addresses",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "address_id", nullable = true)
    )
    private Set<Address> deliveryAddresses = new HashSet<>();

    // by default FetchType LAZY, so implement graph entity for this to avoid future queries
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public User(String username, String password, String fullName, LocalDate birthday, String role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.birthday = birthday;
        this.role = role;
    }

    public User(String username, String password, String fullName, LocalDate birthday, String role, Set<Address> billingAddresses,
                Set<Address> deliveryAddresses,
                List<Order> orders) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.birthday = birthday;
        this.role = role;
        if (billingAddresses != null) this.billingAddresses = billingAddresses;
        if (deliveryAddresses != null) this.deliveryAddresses = deliveryAddresses;
        if (orders != null) this.orders = orders;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
