package group7.service;
//import group7.dto.UpdateOrderRequestDto;
import group7.entity.Order;

        import java.util.List;

public interface OrderService {
   List<Order> getAllOrders();

   Order getOrderById(Long id);

   //void updateOrder(UpdateOrderRequestDTO updateOrderRequestDTO);

   Order createOrder(Long userId);

   Boolean deleteOrderById(Long id);

   List<Order> findOrdersByUserIdWithItems(Long userId);
}
