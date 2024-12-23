package group7.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CrateResponseDTO extends BeverageResponseDTO{

    private Long id;
    @URL
    private String cratePic;
    @PositiveOrZero(message = "crates in stock must be 0 or greater")
    private int cratesInStock;
    @Positive(message = "number of bottles must be greater than 0")
    private int noOfBottles;
    private BottleResponseDTO bottle;
}
