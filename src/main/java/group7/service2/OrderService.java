package group7.service2;
import group7.dto.OrderRequestDto;
import group7.dto.OrderResponseDto;
//import group7.dto.UpdateOrderRequestDto;
import group7.entity.Order;
import group7.users.User;

import java.util.List;

public interface OrderService {
   List<Order> getAllOrders();

   Order getOrderById(Long id);

   //void updateOrder(UpdateOrderRequestDTO updateOrderRequestDTO);

   Order createOrder(Long userId);

   Boolean deleteOrderById(Long id);
}
