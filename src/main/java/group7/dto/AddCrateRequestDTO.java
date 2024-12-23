package group7.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AddCrateRequestDTO extends AddBeverageRequestDTO {

    @URL
    private String cratePic;
    @PositiveOrZero(message = "crates in stock must be 0 or greater")
    private int cratesInStock;
    @Positive(message = "number of bottles must be greater than 0")
    private int noOfBottles;
    private Long bottle;

}
