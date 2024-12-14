package group7.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "beverage")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Use this to allow inheritance
@DiscriminatorColumn(name = "beverage_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor // Write a constructor that has no arguments automatically
@AllArgsConstructor // Write a constructor that has all arguments automatically
@Data // Write all getters and setters automatically
public abstract class Beverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Beverage name must contain only letters and digits")
    @Column(name = "name", nullable = false)
    private String name;

    @Positive(message = "Price must be greater than 0")
    @Column(name = "price")
    private double price;

}
