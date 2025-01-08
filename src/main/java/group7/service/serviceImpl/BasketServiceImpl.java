//package group7.service.serviceImpl;
//
//import group7.component.Basket;
//import group7.dto.*;
//import group7.service.*;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BasketServiceImpl implements BasketService {
//
//    private final BeverageService beverageService;
//    private final OrderService orderService;
//    private final UserService userService;
//    private final OrderItemService orderItemService;
//
//    @Autowired
//    public BasketServiceImpl(BeverageService beverageService, OrderService orderService, UserService userService, OrderItemService orderItemService) {
//        this.beverageService = beverageService;
//        this.orderService = orderService;
//        this.userService = userService;
//        this.orderItemService = orderItemService;
//    }
//
//    @Override
//    public void createOrderFromBasket(Long userId, HttpSession session) {
//        UserResponseDTO userResponseDTO = userService.findUserById(userId);
//        if (userResponseDTO == null) {
//            throw new IllegalArgumentException("User not found");
//        }
//
//        List<AddOrderItemRequestDTO> orderItems = orderItemService.getBeveragesFromBasketAsOrderItems(getBasket(session));
//
//        CreateOrderRequestDTO createOrderRequestDTO = getCreateOrderRequestDTO(orderItems, userResponseDTO);
//
//        orderService.createOrder(createOrderRequestDTO);
//
//        for (AddOrderItemRequestDTO orderItem : orderItems) {
//            orderItem.setOrder(createOrderRequestDTO);
//        }
//
//        clearBasket(session);
//    }
//
//    private static CreateOrderRequestDTO getCreateOrderRequestDTO(List<AddOrderItemRequestDTO> orderItems, UserResponseDTO userResponseDTO) {
//        if (orderItems.isEmpty()) {
//            throw new IllegalStateException("Basket is empty. Cannot create an order.");
//        }
//
//        double totalPrice = 0.0;
//
//        for (AddOrderItemRequestDTO orderItem : orderItems) {
//            totalPrice += orderItem.getPrice();
//        }
//
//        CreateOrderRequestDTO createOrderRequestDTO = new CreateOrderRequestDTO();
//        createOrderRequestDTO.setOrderItems(orderItems);
//        createOrderRequestDTO.setPrice(totalPrice);
//        createOrderRequestDTO.setUser(userResponseDTO);
//        return createOrderRequestDTO;
//    }
//
//    @Override
//    public Basket getBasket(HttpSession session) {
//        Basket basket = (Basket) session.getAttribute("basket");
//        if (basket == null) {
//            basket = new Basket();
//            session.setAttribute("basket", basket);
//        }
//        return basket;
//    }
//
//    @Override
//    public void addBeverageToBasketById(Long beverageId, HttpSession session) {
//        Basket basket = getBasket(session);
//        basket.addBeverage(beverageService.findBeveragesById(beverageId));
//    }
//
//    @Override
//    public void addBeveragesToBasket(List<BeverageResponseDTO> beverages, HttpSession session) {
//        Basket basket = getBasket(session);
//        for (BeverageResponseDTO beverage : beverages) {
//            basket.addBeverage(beverage);
//        }
//    }
//
//    @Override
//    public void clearBasket(HttpSession session) {
//        Basket basket = getBasket(session);
//        basket.clearBeverages();
//    }
//
//}
