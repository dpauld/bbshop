package group7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Entity
@NoArgsConstructor // Write a constructor that has no arguments automatically
@Data // Write all getters and setters automatically
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("CRATE") // Discriminator value to differentiate in the table
public class Crate extends Beverage {

    @NotEmpty
    @URL(message = "Must be a valid URL")
    @Column(name = "crate_pic")
    private String cratePic;

    @Min(value = 0, message = "Quantity must be greater than or equal to zero")
    @Column(name = "crates_in_stock")
    private int cratesInStock;

    @Positive(message = "Number of bottles must be greater than 0")
    @Column(name = "no_of_bottles")
    private int noOfBottles;

    @ManyToOne
    @JoinColumn(name = "bottle_id")
    private Bottle bottle;

    public Crate(
            String name,
            double price,
            String cratePic,
            int cratesInStock,
            int noOfBottles,
            Bottle bottle
    ) {
        super(name, price);

        this.cratePic = cratePic;
        this.cratesInStock = cratesInStock;
        this.noOfBottles = noOfBottles;
        this.bottle = bottle;
    }
}
