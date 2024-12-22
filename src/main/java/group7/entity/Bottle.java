package group7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor // Write a constructor that has no arguments automatically
@Data // Write all getters and setters automatically
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("BOTTLE") // Discriminator value to differentiate in the table
public class Bottle extends Beverage {

    @NotNull
    @NotEmpty
    @URL
    @Column(name = "bottle_pic")
    private String bottlePic;

    @Positive
    @Column(name = "volume", nullable = false)
    private double volume;

    @Column(name = "is_alcoholic")
    private boolean isAlcoholic;

    @Column(name = "volume_percent", nullable = false)
    @PositiveOrZero
    private double volumePercent;

    @NotNull
    @NotEmpty
    @Column(name = "supplier", nullable = false)
    private String supplier;

    @PositiveOrZero
    @Column(name = "in_stock",nullable = false)
    private int inStock;

    // Automatically set isAlcoholic based on volumePercent
    @PrePersist
    @PreUpdate
    private void updateIsAlcoholic() {
        this.isAlcoholic = this.volumePercent > 0.0;
    }

    public Bottle(
            String name,
            double price,
            String bottlePic,
            double volume,
            boolean isAlcoholic,
            double volumePercent,
            String supplier,
            int inStock
    ) {
        super(name, price);

        this.bottlePic = bottlePic;
        this.volume = volume;
        this.isAlcoholic = isAlcoholic;
        this.volumePercent = volumePercent;
        this.supplier = supplier;
        this.inStock = inStock;
    }
}
