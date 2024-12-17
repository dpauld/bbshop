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
public class BottleRequestDTO extends BeverageRequestDTO{

   @URL
   private String bottlePic;
   @Positive
   private double volume;
   private boolean isAlcoholic;
   private double volumePercent;
   private String supplier;
   @PositiveOrZero
   private int inStock;

}
