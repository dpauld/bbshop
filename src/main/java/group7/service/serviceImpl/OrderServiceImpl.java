package group7.service.serviceImpl;

import group7.component.Basket;
import group7.configuration.customClasses.CustomModelMapper;
import group7.dto.*;
import group7.entity.Beverage;
import group7.entity.Order;
import group7.entity.OrderItem;
import group7.entity.User;
import group7.exception.MissingAddressException;
import group7.exception.OrderCancellationException;
import group7.exception.ResourceNotFoundException;
import group7.repository.OrderItemRepository;
import group7.repository.OrderRepository;
import group7.service.OrderService;
import group7.service.BasketService;
import group7.service.BeverageService;
import group7.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final CustomModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final BasketService basketService;
    private final UserService userService;
    private final BeverageService beverageService;

    @Autowired
    public OrderServiceImpl(CustomModelMapper modelMapper, OrderRepository orderRepository, OrderItemRepository orderItemRepository, BasketService basketService, UserService userService, BeverageService beverageService) {
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.basketService = basketService;
        this.userService = userService;
        this.beverageService = beverageService;
    }

    @Transactional
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return order;
    }

    @Transactional
    @Override
    public Order createOrder(Long userId){
        User user = userService.getUserById(userId);
        if (user.getDeliveryAddresses().isEmpty()
                && user.getBillingAddresses().isEmpty()) {
            throw new MissingAddressException("Please provide your delivery and billing address.");
        }

        Basket basket = basketService.getBasket();
        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;
        //log.info(basket.toString());
        for (BasketItemDto basketItem : basket.getItems()) {
            BeverageResponseDto beverageRespDto = basketItem.getBeverage();
            //Beverage beverage = modelMapper.map(beverageRespDto, Beverage.class);//model mapper producing error
            Beverage beverage = beverageService.getBeverageById(beverageRespDto.getId()); //so alternative
            // Create order item
            OrderItem orderItem = new OrderItem();
            Integer quantity = basketItem.getQuantity();
            orderItem.setQuantity(quantity);
            Double unitPrice = beverageRespDto.getPrice();
            orderItem.setPrice(unitPrice);
            orderItem.setBeverage(beverage);
            //might need to change when I know what position actually is.
            orderItem.setPosition(Integer.toString(quantity));
            orderItems.add(orderItem);
            // Calculate total price
            totalPrice += unitPrice * quantity;
        }

        Order order = new Order();
        order.setPrice(totalPrice);
        order.setUser(user);
        order.setOrderItems(orderItems);
        orderItems.forEach(item -> item.setOrder(order));
        orderRepository.save(order);

        //once order is saved update the stock
        beverageService.updateStock();
        //if everything went right, time to clear the basket
        basket.clearBasket();
        return order;
    }

    @Transactional
    @Override
    public void cancelOrder(Long orderId) {
        // Retrieve the order by ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with ID " + orderId + " not found."));
        String orderStatus = order.getOrderStatus();
        if (orderStatus.equalsIgnoreCase("confirmed") ||
                orderStatus.equalsIgnoreCase("shipped") ||
                orderStatus.equalsIgnoreCase("delivered")||
                orderStatus.equalsIgnoreCase("completed")||
                orderStatus.equalsIgnoreCase("cancelled")
        ) {
            throw new OrderCancellationException("Cannot cancel an order that has already been in processing.");
        }
        order.setOrderStatus("Cancelled");
        // Add the quantities back to stock for each item in the order
        for (OrderItem orderItem : order.getOrderItems()) {
            Beverage beverage = orderItem.getBeverage();
            log.info(beverage.toString());
            if (beverage != null) {
                int updatedStock = beverage.getInStock() + orderItem.getQuantity();
                beverage.setInStock(updatedStock);
                beverageService.update(beverage); // Save the updated stock
            }
        }
        // Mark the order as cancelled
        orderRepository.save(order); // Persist the changes
    }

//    @Override
//    public void updateOrder(UpdateOrderRequestDTO updateOrderRequestDTO) {
//        try {
//            Long id = updateOrderRequestDTO.getId();
//            Order orderToEdit = orderRepository.findById(id)
//                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
//            orderToEdit.setPrice(updateOrderRequestDTO.getPrice());
//            orderRepository.save(orderToEdit);
//        } catch (ResourceNotFoundException e) {
//            throw new OrderUpdateException("Order not found with id: " + updateOrderRequestDTO.getId(), e);
//        }
//    }

    @Override
    public Boolean deleteOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
        return !orderRepository.existsById(id);
    }

    @Transactional
    @Override
    public List<Order> findOrdersByUserIdWithItems(Long userId) {
        return orderRepository.findOrdersByUserIdWithItems(userId);
    }
}
