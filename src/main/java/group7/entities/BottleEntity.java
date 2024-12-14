package group7.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.URL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bottle")
@NoArgsConstructor // Write a constructor that has no arguments automatically
@AllArgsConstructor // Write a constructor that has all arguments automatically
@Data // Write all getters and setters automatically
public class BottleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Bottle name must contain only letters and digits")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty
    @URL(message = "Must be a valid URL")
    @Column(name = "bottle_pic")
    private String bottlePic;

    @Positive(message = "Volume must be greater than 0")
    @Column(name = "volume")
    private double volume;

    @Column(name = "is_alcoholic", nullable = false)
    private boolean isAlcoholic;

    @Column(name = "volume_percent")
    private double volumePercent;

    @Positive(message = "Price must be greater than 0")
    @Column(name = "price")
    private double price;

    @NotEmpty
    @Column(name = "supplier", nullable = false)
    private String supplier;

    @NotEmpty
    @Min(value = 0, message = "Quantity must be greater than or equal to zero")
    @Column(name = "in_stock", nullable = false)
    private int inStock;

    // Automatically set isAlcoholic based on volumePercent
    @PrePersist
    @PreUpdate
    private void updateIsAlcoholic() {this.isAlcoholic = this.volumePercent > 0.0;}
}
