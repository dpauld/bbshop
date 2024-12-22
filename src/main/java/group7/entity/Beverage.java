package group7.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "beverage")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Use this to allow inheritance
@DiscriminatorColumn(name = "beverage_type", discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class Beverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "name", nullable = false)
    protected String name;

    @Positive
    @Column(name = "price",nullable = false)
    protected double price;

    protected Beverage(String name, double price) {
        this.name = name;
        this.price = price;
    }

}
