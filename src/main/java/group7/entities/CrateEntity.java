package group7.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@NoArgsConstructor // Write a constructor that has no arguments automatically
@AllArgsConstructor // Write a constructor that has all arguments automatically
@Data // Write all getters and setters automatically
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("CRATE") // Discriminator value to differentiate in the table
public class CrateEntity extends BeverageEntity {

    // id, name and price comes from BeverageEntity

    @NotEmpty
    @URL(message = "Must be a valid URL")
    @Column(name = "crate_pic")
    private String cratePic;

    @NotEmpty
    @Min(value = 0, message = "Quantity must be greater than or equal to zero")
    @Column(name = "crates_in_stock", nullable = false)
    private int cratesInStock;

    @Positive(message = "Number of bottles must be greater than 0")
    @Column(name = "no_of_bottles")
    private int noOfBottles;

    @ManyToOne
    @JoinColumn(name = "bottle_id")
    private BottleEntity bottle;

}
