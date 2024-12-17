package group7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Positive(message = "Price must be greater than 0")
    @Column(name = "price")
    private double price;

    @ManyToOne
    //@JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) //Bidirectional
    private List<OrderItem> orderItems;

    public Order(double price, User user, List<OrderItem> orderItems) {
        this.price = price;
        this.user = user;
        this.orderItems = orderItems;
    }
}
