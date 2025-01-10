package group7.service;

import group7.dto.BeverageCreateDto;
import group7.entity.*;
import group7.exception.ResourceNotFoundException;
import group7.repository.AddressRepository;
import group7.repository.UserRepository;
import group7.users.RegistrationForm;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    private static User user;
    private static Beverage createdBottle;

    @BeforeAll
    public static void init(
            @Autowired UserRepository userRepository,
            @Autowired BeverageService beverageService,
            @Autowired AddressRepository addressRepository,
            @Autowired PasswordEncoder passwordEncoder
    ) {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setUsername("User 1");
        registrationForm.setPassword("Ir0bn2Wk8T8K1U");
        registrationForm.setFullName("Eva Ning");
        registrationForm.setBirthday(LocalDate.of(2001, 5, 16));
        User user = registrationForm.toUser(passwordEncoder, "ROLE_USER");

        Address billingAddress = new Address("Metusaliquet", "10", "96028");
        Address  deliveryAddress = new Address("Nuncvel", "8", "20536");
        addressRepository.save(billingAddress);
        addressRepository.save(deliveryAddress);

        user.setBillingAddresses(Set.of(billingAddress));
        user.setDeliveryAddresses(Set.of(deliveryAddress));

        OrderServiceIntegrationTest.user = userRepository.save(user);

        BeverageCreateDto beverage = getSampleBeverage();
        OrderServiceIntegrationTest.createdBottle = beverageService.createBottle(beverage);
    }

    @BeforeEach
    public void setUp(@Autowired BasketService basketService) {
        basketService.getBasket();
        basketService.clearBasket();
        basketService.addItemToBasket(createdBottle.getId());
    }

    private static BeverageCreateDto getSampleBeverage() {
        BeverageCreateDto beverage = new BeverageCreateDto();
        beverage.setName("Maximusnec");
        beverage.setPrice(10.0);
        beverage.setInStock(10);
        beverage.setPicture(
                "https://dictionary.cambridge.org/de/images/thumb/bottle_noun_002_04218.jpg?version=6.0.43"
        );
        beverage.setVolume(1.0);
        beverage.setAlcoholic(false);
        beverage.setVolumePercent(0.0);
        beverage.setSupplier("Justofeugiat");
        beverage.setNoOfBottles(1);

        return beverage;
    }

    @Test
    @Transactional
    public void testCreateOrder() {
        assertEquals(0, orderService.getAllOrders().size());

        Order order = orderService.createOrder(user.getId());
        Order foundOrder = orderService.getOrderById(order.getId());

        assertNotNull(foundOrder);
        assertEquals(1, orderService.getAllOrders().size());
    }

    @Test
    @Transactional
    public void testFindOrdersByUserIdWithItems() {
        orderService.createOrder(user.getId());

        List<Order> orders = orderService.findOrdersByUserIdWithItems(user.getId());

        assertNotNull(orders);
        assertEquals(1, orders.size());
    }

    @Test
    @Transactional
    public void testDeleteOrderById() {
        Order order = orderService.createOrder(user.getId());
        assertEquals(1, orderService.getAllOrders().size());

        orderService.deleteOrderById(order.getId());
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(order.getId()));

        assertEquals(0, orderService.getAllOrders().size());
    }

    @Test
    @Transactional
    public void testCancelOrder() {
        Order order = orderService.createOrder(user.getId());
        orderService.cancelOrder(order.getId());

        assertEquals("Canceled", orderService.getOrderById(order.getId()).getOrderStatus());
    }
}
