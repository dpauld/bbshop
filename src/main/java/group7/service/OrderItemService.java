package group7.service;

import group7.component.Basket;
import group7.dto.AddOrderItemRequestDTO;

import java.util.List;

public interface OrderItemService {


    public List<AddOrderItemRequestDTO> getBeveragesFromBasketAsOrderItems(Basket basket);

}
