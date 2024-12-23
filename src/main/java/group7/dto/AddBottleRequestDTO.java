package group7.dto;

import group7.validation.annotation.ValidVolumeAlcoholic;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ValidVolumeAlcoholic
public class AddBottleRequestDTO extends AddBeverageRequestDTO {

   @URL
   private String bottlePic;
   @Positive(message = "volume must be greater than 0")
   private double volume;
   private boolean isAlcoholic;
   @PositiveOrZero(message = "Volume percent must be 0 or greater")
   private double volumePercent;
   @NotNull(message = "Name cannot be null")
   @NotEmpty(message = "Name cannot be empty")
   private String supplier;
   @PositiveOrZero(message = "number of bottles in stock must be greater than 0")
   private int inStock;

}
