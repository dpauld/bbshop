package group7.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {

    private Long id;
    @Pattern(regexp = "\\d+", message = "Field must contain only digits")
    private String position;
    @Positive(message = "price must be greater than 0")
    private double price;
    private BeverageResponseDTO beverage;
    private OrderResponseDTO order;

}
