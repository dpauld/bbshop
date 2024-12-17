package group7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CrateResponseDTO extends BeverageResponseDTO{

    private Long id;
    private String cratePic;
    private int cratesInStock;
    private int noOfBottles;
    private BottleResponseDTO bottle;
}
