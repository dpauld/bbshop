package group7.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@NoArgsConstructor // Write a constructor that has no arguments automatically
@AllArgsConstructor // Write a constructor that has all arguments automatically
@Data // Write all getters and setters automatically
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "position")
    @Pattern(regexp = "^[0-9]+$", message = "Position must contain only digits")
    private String position;

    @Positive(message = "Price must be greater than 0")
    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "beverage_id")
    private Beverage beverage;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

}
