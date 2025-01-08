package group7.entity;

import group7.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
//@NamedEntityGraph(
//        name = "Order.orders",
//        attributeNodes = {
//                @NamedAttributeNode("orderItems")
//        })
@NamedEntityGraph(
        name = "Order.withItemsAndBeverage",
        attributeNodes = {
                @NamedAttributeNode(value= "orderItems", subgraph = "orderItem.withBeverage")
        },
        subgraphs = @NamedSubgraph(
                name="orderItem.withBeverage",
                attributeNodes = {
                        @NamedAttributeNode("beverage")
                })
)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Positive(message = "price must be greater than 0")
    @Column(name = "price", nullable = false)
    private double price;

    //Merge will make sure, any changes made to user should reflect here.
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // by default FetchType LAZY, so implement graph entity for this to avoid future queries
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(double price, User user, List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item.");
        }
        this.price = price;
        this.user = user;
        this.orderItems = orderItems;
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("OrderItem cannot be null.");
        }
        item.setOrder(this); // Maintain the bidirectional relationship
        this.orderItems.add(item);
    }

    public void removeItem(OrderItem item) {
        //if it's last item then maybe delete the whole order.
        if (this.orderItems.size() <= 1) {
            //throw new IllegalStateException("Cannot remove the last item from the order.");
        }
        this.orderItems.remove(item);
        item.setOrder(null); // Break the bidirectional relationship
    }
}
