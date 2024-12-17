package group7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {

    private String position;
    private double price;
    private BeverageRequestDTO beverage;
    private CreateOrderRequestDTO order;





}
