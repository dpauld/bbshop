package group7.service;


import group7.component.Basket;
import group7.dto.BasketItemDto;
import group7.exception.ResourceNotFoundException;

import java.util.List;

public interface BasketService {

    void addItemToBasket(Long beverageId) throws ResourceNotFoundException;

    void removeItemFromBasket(Long beverageId) throws ResourceNotFoundException;

    boolean updateItemQuantity(Long beverageId, int quantity) throws ResourceNotFoundException;

    Long checkout();

    Basket getBasket();

    void clearBasket();

    List<BasketItemDto> getItemsInBasket();

    double getTotalPrice();

    public boolean isBasketEmpty();
}
