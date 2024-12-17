package group7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "beverage")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Use this to allow inheritance
@DiscriminatorColumn(name = "beverage_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@Data
public abstract class Beverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Beverage name must contain only letters and digits")
    @Column(name = "name", nullable = false)
    protected String name;

    @Positive(message = "Price must be greater than 0")
    @Column(name = "price")
    protected double price;

    protected Beverage(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
