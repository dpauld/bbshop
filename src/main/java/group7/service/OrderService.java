package group7.service;

import group7.dto.CreateOrderRequestDTO;
import group7.dto.OrderResponseDTO;
import group7.dto.UpdateOrderRequestDTO;
import group7.entity.Order;

import java.util.List;

public interface OrderService {

   OrderResponseDTO orderToOrderResponseDTO(Order order);

   List<OrderResponseDTO> ordersToOrderResponseDTOList(List<Order> orders);

   Order createOrderRequestDTOToOrder(CreateOrderRequestDTO createOrderRequestDTO);

   List<Order> createOrderRequestDTOListToOrders(List<CreateOrderRequestDTO> createOrderRequestDTOs);

   List<OrderResponseDTO> getAllOrders();

   OrderResponseDTO getOrderById(Long id);

   void updateOrder(UpdateOrderRequestDTO updateOrderRequestDTO);

   OrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO);

   void deleteOrderById(Long id);

}
