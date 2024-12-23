package group7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;


@Entity
@NoArgsConstructor // Write a constructor that has no arguments automatically
@Data // Write all getters and setters automatically
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("CRATE") // Discriminator value to differentiate in the table
public class Crate extends Beverage {

    @NotNull
    @NotEmpty
    @URL
    @Column(name = "crate_pic")
    private String cratePic;

    @Positive
    @Column(name = "crates_in_stock", nullable = false)
    private int cratesInStock;

    @Positive
    @Column(name = "no_of_bottles", nullable = false)
    private int noOfBottles;

    @ManyToOne
    @JoinColumn(name = "bottle_id",nullable = true)
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
