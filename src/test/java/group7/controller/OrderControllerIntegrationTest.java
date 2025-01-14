package group7.controller;

import group7.dto.OrderResponseDTO;
import group7.entity.Order;
import group7.entity.User;
import group7.service.OrderService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static util.AuthenticationManager.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerIntegrationTest {

    private static final String BASE_PATH = "/orders";

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private OrderService orderService;

    private User user;

    @BeforeEach
    public void setUp(@Autowired PasswordEncoder passwordEncoder) throws Exception {
        mvc.perform(createRegistrationPostRequest().with(csrf()));
        mvc.perform(createLoginPostRequest().with(csrf()));

        user = getAuthenticatedUser(passwordEncoder, "ROLE_ADMIN");
    }

    @Test
    @Transactional
    public void testCreateOrder() throws Exception {
        Order mockOrder = new Order();
        mockOrder.setId(1L);

        when(orderService.createOrder(user.getId())).thenReturn(mockOrder);

        mvc.perform(post(BASE_PATH)
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(BASE_PATH))
                .andExpect(flash().attribute("message", "Order created successfully!"));

        verify(orderService, times(1)).createOrder(user.getId());
    }

    @Test
    @Transactional
    public void testGetOrderById() throws Exception {
        Long mockOrderId = 1L;
        OrderResponseDTO mockOrder = new OrderResponseDTO();
        mockOrder.setId(mockOrderId);
        when(orderService.getOrderById(mockOrderId)).thenReturn(mockOrder);

        mvc.perform(get(BASE_PATH + "/{id}", mockOrderId)
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("order"))
                .andExpect(model().attribute("order", mockOrder));
    }

    @Test
    @Transactional
    public void testGetAllOrders() throws Exception {
        List<OrderResponseDTO> orders = Arrays.asList(new OrderResponseDTO(), new OrderResponseDTO());
        when(orderService.getAllOrders()).thenReturn(orders);

        mvc.perform(get(BASE_PATH)
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("orders"))
                .andExpect(model().attribute("orders", orders));
    }

    @Test
    @Transactional
    public void testCancelOrder() throws Exception {
        Long orderId = 1L;
        doNothing().when(orderService).cancelOrder(orderId);

        mvc.perform(post(BASE_PATH + "/{id}/cancel", orderId)
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(BASE_PATH))
                .andExpect(flash().attribute("success", "Order canceled successfully!"));

        verify(orderService, times(1)).cancelOrder(orderId);
    }

    @Test
    @Transactional
    public void testDeleteOrderById() throws Exception {
        Long orderId = 1L;
        when(orderService.deleteOrderById(orderId)).thenReturn(true);

        mvc.perform(delete(BASE_PATH + "/{id}", orderId)
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(BASE_PATH));

        verify(orderService, times(1)).deleteOrderById(orderId);
    }
}
