package group7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor // Write a constructor that has no arguments automatically
@Data // Write all getters and setters automatically
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("BOTTLE") // Discriminator value to differentiate in the table
public class Bottle extends Beverage {

    @Positive(message = "volume must be greater than 0")
    @Column(name = "volume", nullable = false)
    private double volume;

    @Column(name = "is_alcoholic")
    private boolean isAlcoholic;

    @Column(name = "volume_percent", nullable = false)
    @PositiveOrZero(message = "volume percent must be positive or zero")
    private double volumePercent;

    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    @NotBlank(message = "supplier is required.")
    @Column(name = "supplier", nullable = false)
    private String supplier;

    // Automatically set isAlcoholic based on volumePercent
    @PrePersist
    @PreUpdate
    private void updateIsAlcoholic() {
        this.isAlcoholic = this.volumePercent > 0.0;
    }

    public Bottle(
            String name,
            double price,
            int inStock,
            String picture,
            double volume,
            boolean isAlcoholic,
            double volumePercent,
            String supplier
    ) {
        super(name, price, inStock, picture);
        this.volume = volume;
        this.isAlcoholic = isAlcoholic;
        this.volumePercent = volumePercent;
        this.supplier = supplier;
    }
}
