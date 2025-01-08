package group7.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BottleCreateDto extends BeverageCreateDto {
    @Positive(message = "volume must be greater than 0")
    private double volume;

    private boolean isAlcoholic;

    @PositiveOrZero(message = "volume percent must be positive or zero")
    private double volumePercent;

    @NotNull(message = "supplier name cannot be null")
    @NotEmpty(message = "supplier name cannot be empty")
    @NotBlank(message = "supplier is required.")
    private String supplier;
}