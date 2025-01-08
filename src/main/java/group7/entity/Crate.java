package group7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@NoArgsConstructor // Write a constructor that has no arguments automatically
@Data // Write all getters and setters automatically
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("CRATE") // Discriminator value to differentiate in the table
public class Crate extends Beverage {
    @Min(value = 1, message = "must be 1 at minimum.")
    @Positive(message = "number of bottles must be greater than 0")
    @Column(name = "no_of_bottles",  nullable = false)
    private int noOfBottles;

    //@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ManyToOne
    @JoinColumn(name = "bottle_id",nullable = true)
    private Bottle bottle;

    public Crate(
            String name,
            double price,
            int inStock,
            String picture,
            int noOfBottles,
            Bottle bottle
    ) {
        super(name, price, inStock, picture);
        this.noOfBottles = noOfBottles;
        this.bottle = bottle;
    }
}
