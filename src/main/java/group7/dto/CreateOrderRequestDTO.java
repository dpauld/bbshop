package group7.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestDTO {

    @Positive(message = "Price must be greater than 0")
    private double price;

    private UserRequestDTO user;

    private List<OrderItemRequestDTO> orderItems;



}
