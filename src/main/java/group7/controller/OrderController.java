package group7.controller;

import group7.dto.CreateOrderRequestDTO;
import group7.dto.OrderResponseDTO;
import group7.dto.UpdateOrderRequestDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface OrderController {

    /**
     * Retrieves a list of all orders.
     * <p>
     * This method fetches all orders and returns a list of their corresponding
     * OrderResponseDTO objects, which contain relevant order data.
     *
     * @return a ResponseEntity containing a list of OrderResponseDTO objects representing all orders.
     */
    ResponseEntity<List<OrderResponseDTO>> getAllOrders();

    /**
     * Retrieves the details of a specific order by its ID.
     * <p>
     * This method fetches the order details for the given ID and returns the
     * corresponding OrderResponseDTO object, which contains the data of the specified order.
     *
     * @param id the ID of the order to be retrieved.
     * @return a ResponseEntity containing the OrderResponseDTO object representing the order.
     */
    ResponseEntity<OrderResponseDTO> getOrderById(Long id);

    /**
     * Creates a new order using the provided order data.
     * <p>
     * This method creates a new order based on the CreateOrderRequestDTO data.
     * After creating the order, it returns an HTTP response indicating the creation status.
     *
     * @param createOrderRequestDTO the DTO containing the information for the new order.
     * @return a ResponseEntity indicating the result of the order creation (e.g., HTTP status CREATED).
     */
    ResponseEntity<OrderResponseDTO> createOrder(CreateOrderRequestDTO createOrderRequestDTO);


    /**
     * Updates the details of an existing order.
     * <p>
     * This method takes the updated information from the UpdateOrderRequestDTO and the ID
     * of the order to be updated. After updating, it returns the updated order as an OrderResponseDTO.
     *
     * @param updateOrderRequestDTO the DTO containing the updated order information.
     * @param id the ID of the order to be updated.
     * @return a ResponseEntity containing the updated OrderResponseDTO.
     */
    ResponseEntity<OrderResponseDTO> updateOrder(Long id, UpdateOrderRequestDTO updateOrderRequestDTO);


    /**
     * Deletes an order by its ID.
     * <p>
     * This method deletes the order corresponding to the given ID. If the deletion
     * is successful, it returns a response indicating no content (204 No Content).
     *
     * @param id the ID of the order to be deleted.
     * @return a ResponseEntity with HTTP status NO_CONTENT if the deletion is successful.
     */
    ResponseEntity<Void> deleteOrderById(Long id);

}
