package group7.dto;

import group7.entity.Bottle;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public class CrateCreateDto extends BeverageCreateDto{
    @Min(value = 1, message = "must be 1 at minimum.")
    @Positive(message = "number of bottles must be greater than 0")
    private Integer noOfBottles;

    private Integer bottleId;

    private Bottle bottle;
}
