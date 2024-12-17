package group7.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CrateRequestDTO extends BeverageRequestDTO{

    private String cratePic;
    @PositiveOrZero
    private int cratesInStock;
    @Positive
    private int noOfBottles;
    private BottleRequestDTO bottle;

}
