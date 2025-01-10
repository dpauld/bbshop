package group7.controller;

import group7.dto.AddressRequestDto;
import group7.entity.Order;
import group7.entity.User;
import group7.service.OrderService;
import group7.service.serviceImpl.UserServiceImpl;
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

import java.util.List;
import java.util.Arrays;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static util.AuthenticationManager.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class UserProfileControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserServiceImpl userService;

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
    public void testGetUserProfile() throws Exception {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);

        mvc.perform(get("/profile")
                        .with(user(user)))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attribute("billingAddresses", user.getBillingAddresses()))
                .andExpect(model().attribute("deliveryAddresses", user.getDeliveryAddresses()));
    }

    @Test
    public void testAddAddress() throws Exception {
        AddressRequestDto addressDto = new AddressRequestDto();
        addressDto.setStreet("123 Test St.");
        addressDto.setNumber("7");
        addressDto.setPostalCode("12345");

        when(userService.findByUsername(user.getUsername())).thenReturn(user);

        mvc.perform(post("/profile/addresses")
                        .with(user(user))
                        .param("type", "billing")
                        .flashAttr("newAddress", addressDto)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        verify(userService, times(1)).addAddressByType(user.getId(), "billing", addressDto);
    }

    @Test
    public void testDeleteAddress() throws Exception {
        Long addressId = 1L;

        mvc.perform(delete("/profile/addresses/{addressId}", addressId)
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        verify(userService, times(1)).removeAddress(addressId, user.getId());
    }

    @Test
    public void testGetOrdersByUser() throws Exception {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderService.findOrdersByUserIdWithItems(user.getId())).thenReturn(orders);

        mvc.perform(get("/profile/orders")
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("orders"))
                .andExpect(model().attribute("orders", orders));
    }

    @Test
    public void testGetUserProfileJson() throws Exception {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);

        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(orderService.findOrdersByUserIdWithItems(user.getId())).thenReturn(orders);

        mvc.perform(get("/profileJson")
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
}
