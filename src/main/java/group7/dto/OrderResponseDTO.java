package group7.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private Long id;
    @Positive(message = "Price must be greater than 0")
    private double price;
    private UserResponseDTO user;
    private List<OrderItemResponseDTO> orderItems;


}
