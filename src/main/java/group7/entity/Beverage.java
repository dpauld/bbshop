package group7.entity;

import group7.validation.group.BeverageGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;


@Entity
@Table(name = "beverage")
@Inheritance(strategy = InheritanceType.JOINED) // Use this to allow inheritance
@DiscriminatorColumn(name = "beverage_type", discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class Beverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    @NotBlank(message = "name is required.")
    @Pattern(regexp = "^[a-zA-Z0-9 ÄäÜüÖöß_-]+$", message = "only letters, digits, - , _ , umlauts allowed.")
    @Column(name = "name", nullable = false)
    protected String name;

    @Positive(message = "price must be greater than 0")
    @Column(name = "price",nullable = false)
    protected double price;

    @PositiveOrZero(message = "number of item in stock must not be negative.")
    @Column(name = "in_stock")
    private int inStock=0;//default value 0, incase user forgets to provide

    @URL(message = "picture must be a valid url.")
    @Column(name = "picture")
    private String picture;

    protected Beverage(String name, double price, int inStock, String picture) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.picture = picture;
    }

}
