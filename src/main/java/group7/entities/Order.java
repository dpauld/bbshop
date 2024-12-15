package group7.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor // Write a constructor that has no arguments automatically
@AllArgsConstructor // Write a constructor that has all arguments automatically
@Data // Write all getters and setters automatically
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Positive(message = "Price must be greater than 0")
    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany
    @JoinTable(
            name = "orders-order_items",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_item_id")}
    )
    private List<OrderItem> orderItems;

}
