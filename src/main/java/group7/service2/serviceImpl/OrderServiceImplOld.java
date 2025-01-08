//package group7.service2.serviceImpl;
//
//import group7.dto.CreateOrderRequestDTO;
//import group7.dto.OrderResponseDTO;
//import group7.dto.UpdateOrderRequestDTO;
//import group7.entity.Order;
//import group7.exception.OrderCreationException;
//import group7.exception.OrderUpdateException;
//import group7.exception.ResourceNotFoundException;
//import group7.repository.OrderItemRepository;
//import group7.repository.OrderRepository;
//import group7.service2.OrderService;
//import jakarta.transaction.Transactional;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class OrderServiceImplOld implements OrderService {
//
//    private final ModelMapper modelMapper;
//    private final OrderRepository orderRepository;
//    private final OrderItemRepository orderItemRepository;
//
//    @Autowired
//    public OrderServiceImpl(ModelMapper modelMapper, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
//        this.modelMapper = modelMapper;
//        this.orderRepository = orderRepository;
//        this.orderItemRepository = orderItemRepository;
//    }
//
//    @Override
//    public OrderResponseDTO orderToOrderResponseDTO(Order order) {
//        return modelMapper.map(order, OrderResponseDTO.class);
//    }
//
//    @Override
//    public List<OrderResponseDTO> ordersToOrderResponseDTOList(List<Order> orders) {
//        return orders.stream()
//                .map(this::orderToOrderResponseDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Order createOrderRequestDTOToOrder(CreateOrderRequestDTO createOrderRequestDTO) {
//        return modelMapper.map(createOrderRequestDTO, Order.class);
//    }
//
//    @Override
//    public List<Order> createOrderRequestDTOListToOrders(List<CreateOrderRequestDTO> createOrderRequestDTOs) {
//        return createOrderRequestDTOs.stream()
//                .map(this::createOrderRequestDTOToOrder)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<OrderResponseDTO> getAllOrders() {
//        return ordersToOrderResponseDTOList(orderRepository.findAll());
//    }
//
//    @Override
//    public OrderResponseDTO getOrderById(Long id) {
//        Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
//        return orderToOrderResponseDTO(order);
//    }
//
//    @Transactional
//    @Override
//    public OrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO) {
//        try {
//            Order order = createOrderRequestDTOToOrder(createOrderRequestDTO);
//            order.setOrderItems(orderItemRepository.saveAll(order.getOrderItems()));  // FIXME org.springframework.orm.ObjectOptimisticLockingFailureException: Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect): [group7.entity.OrderItem#1]
//            Order savedOrder = orderRepository.save(order);
//            return orderToOrderResponseDTO(savedOrder);
//        } catch (Exception e) {
//            // Order creation failed, throw a specific exception with a detailed message
//            throw new OrderCreationException("Order creation failed", e);
//        }
//    }
//
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
//
//    @Override
//    public void deleteOrderById(Long id) {
//        if (!orderRepository.existsById(id)) {
//            throw new ResourceNotFoundException("Order not found with id: " + id);
//        }
//        orderRepository.deleteById(id);
//    }
//}
