package group7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
@Data
//@NamedEntityGraph(
//        name = "OrderItem.beverage",
//        attributeNodes = @NamedAttributeNode("beverage")
//)
//no need, as by default eager, no need to overwrite
//@NamedEntityGraph(
//        name = "OrderItem.withBeverageAndOrder",
//        attributeNodes = {
//                @NamedAttributeNode("beverage"),
//                @NamedAttributeNode("order")
//        }
//)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "position")
    @Pattern(regexp = "^[0-9]+$")
    private String position;

    @Positive
    @Column(name = "price")
    private double price;

    @Positive
    @Column(name = "quantity")
    private Integer quantity;

    //default fetch type eager, so all data will be fetched initially. Thus, not requiring future queries.
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "beverage_id",nullable = true)
    private Beverage beverage;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderItem(String position, double price, Beverage beverage, Order order) {
        this.position = position;
        this.price = price;
        this.beverage = beverage;
        this.order = order;
    }
}