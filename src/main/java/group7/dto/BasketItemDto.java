package group7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketItemDto {
    private BeverageResponseDto beverage;
    private Integer quantity;

    //Above attributes are similar to OrderItem, but we won't have price, as we from beverage we will be having the latest price.
    //In case of orderItem we are storing price, because in future Beverage price might change,
    // but the price of the Item Order 3 months ago should not be changing.
}