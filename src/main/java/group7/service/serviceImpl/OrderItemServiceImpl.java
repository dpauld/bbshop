//package group7.service.serviceImpl;
//
//import group7.component.Basket;
//import group7.dto.AddOrderItemRequestDTO;
//import group7.dto.BeverageResponseDTO;
//import group7.service.OrderItemService;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class OrderItemServiceImpl implements OrderItemService {
//
//    @Override
//    public List<AddOrderItemRequestDTO> getBeveragesFromBasketAsOrderItems(Basket basket) {
//        List<BeverageResponseDTO> beveragesInBasket = basket.getBeverages();
//
//        if (beveragesInBasket.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        List<AddOrderItemRequestDTO> orderItems = new ArrayList<>();
//        for (int i = 0; i < beveragesInBasket.size(); i++) {
//            BeverageResponseDTO beverage = beveragesInBasket.get(i);
//
//            AddOrderItemRequestDTO addOrderItemRequestDTO = new AddOrderItemRequestDTO();
//            addOrderItemRequestDTO.setPosition(String.valueOf(i + 1));
//            addOrderItemRequestDTO.setPrice(beverage.getPrice());
//            addOrderItemRequestDTO.setBeverage(beverage);
//
//            orderItems.add(addOrderItemRequestDTO);
//        }
//        return orderItems;
//    }
//
//
//}
