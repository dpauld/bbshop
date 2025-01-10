//package group7.service2.serviceImpl.service;
//import group7.component.Basket;
//import group7.configuration.customClasses.CustomModelMapper;
//import group7.dto.BeverageResponseDto;
//import group7.entity.Beverage;
//import group7.repository.BeverageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class BsktServiceImpl {
//
//    @Autowired
//    private Basket basket;
//    @Autowired
//    private BeverageRepository beverageRepository;
//    @Autowired
//    private CustomModelMapper modelMapper;
//
//    public void addBeverageToBasket(Long beverageId) {
//        Optional<Beverage> beverageOptional = beverageRepository.findById(beverageId);
//        if (beverageOptional.isPresent()) {
//            Beverage beverage = beverageOptional.get();
//            BeverageResponseDto beverageDto = modelMapper.map(beverage, BeverageResponseDto.class);
//            basket.addBeverage(beverageDto);
//        } else {
//            throw new RuntimeException("Beverage not found");
//        }
//    }
//
//    public void removeBeverageFromBasket(Long beverageId) {
//        basket.getBeverages().keySet().removeIf(beverage -> beverage.getId().equals(beverageId));
//    }
//
//    public boolean updateBeverageQuantity(Long beverageId, int quantity) {
//        Optional<Beverage> beverageOptional = beverageRepository.findById(beverageId);
//        if (beverageOptional.isPresent()) {
//            Beverage beverage = beverageOptional.get();
//            if (quantity <= beverage.getInStock()) {
//                BeverageResponseDto beverageDto = modelMapper.map(beverage, BeverageResponseDto.class);
//                basket.updateQuantity(beverageDto, quantity);
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            throw new RuntimeException("Beverage not found");
//        }
//    }
//
//    public void clearBasket() {
//        basket.clearBasket();
//    }
//
//    public Map<BeverageResponseDto, Integer> getBeveragesInBasket() {
//        return basket.getBeverages();
//    }
//
//    public double getTotalPrice() {
//        return basket.getTotalPrice();
//    }
//}

package group7.service.serviceImpl;

import group7.component.Basket;
import group7.configuration.customClasses.CustomModelMapper;
import group7.dto.BasketItemDto;
import group7.dto.BeverageResponseDto;
import group7.entity.Beverage;
import group7.exception.ResourceNotFoundException;
import group7.repository.BeverageRepository;
import group7.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketServiceImpl implements BasketService {

    private Basket basket;
    private BeverageRepository beverageRepository;
    private CustomModelMapper modelMapper;

    @Autowired
    public BasketServiceImpl(Basket basket, BeverageRepository beverageRepository, CustomModelMapper modelMapper) {
        this.basket = basket;
        this.beverageRepository = beverageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addItemToBasket(Long beverageId) {
        Beverage beverage = beverageRepository.findById(beverageId).orElseThrow(()-> new ResourceNotFoundException("Beverage", "id", beverageId));
        BeverageResponseDto beverageDto = modelMapper.map(beverage, BeverageResponseDto.class);
        basket.addBeverage(beverageDto);
    }

    @Override
    public void removeItemFromBasket(Long beverageId) {
        Beverage beverage = beverageRepository.findById(beverageId).orElseThrow(()-> new ResourceNotFoundException("Beverage", "id", beverageId));
        BeverageResponseDto beverageDto = modelMapper.map(beverage, BeverageResponseDto.class);
        basket.removeBeverage(beverageDto);
    }

    @Override
    public boolean updateItemQuantity(Long beverageId, int quantity) {
        Beverage beverage = beverageRepository.findById(beverageId).orElseThrow(()-> new ResourceNotFoundException("Beverage", "id", beverageId));
        if (quantity > beverage.getInStock()) {
            return false;
        }
        BeverageResponseDto beverageDto = modelMapper.map(beverage, BeverageResponseDto.class);
        basket.updateQuantity(beverageDto, quantity);
        return true;
    }

    @Override
    public Long checkout() {
        List<BasketItemDto> beverages = getItemsInBasket();
        for (BasketItemDto item : beverages) {
            BeverageResponseDto beverage = item.getBeverage();
            int quantity = item.getQuantity();
            if (quantity > beverage.getInStock()) {
                return beverage.getId();//if quantity exceeds current limit than
            }
        }
        //orderService.createOrder();
        clearBasket();
        //returning null means no beverage id found exceeding inStock.
        return null;
    }

    @Override
    public Basket getBasket() {
        return basket;
    }

    @Override
    public void clearBasket() {
        basket.clearBasket();
    }

    @Override
    public List<BasketItemDto> getItemsInBasket() {
        return basket.getItems();
    }

    @Override
    public double getTotalPrice() {
        return basket.getTotalPrice();
    }

    @Override
    public boolean isBasketEmpty() {
        if (basket == null || basket.getItems().isEmpty()) {
            return true;
        }
        return false;
    }
}