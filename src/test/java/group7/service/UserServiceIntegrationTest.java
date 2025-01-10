package group7.service;

import group7.dto.AddressRequestDto;
import group7.entity.*;
import group7.repository.AddressRepository;
import group7.repository.BeverageRepository;
import group7.repository.OrderRepository;
import group7.repository.UserRepository;
import group7.users.RegistrationForm;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    private static User user;
    private static Address billingAddress;

    @BeforeAll
    public static void init(
            @Autowired UserRepository userRepository,
            @Autowired BeverageRepository beverageRepository,
            @Autowired OrderRepository orderRepository,
            @Autowired AddressRepository addressRepository,
            @Autowired PasswordEncoder passwordEncoder
    ) {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setUsername("User 1");
        registrationForm.setPassword("Ir0bn2Wk8T8K1U");
        registrationForm.setFullName("Eva Ning");
        registrationForm.setBirthday(LocalDate.of(2001, 5, 16));
        User user = registrationForm.toUser(passwordEncoder, "ROLE_USER");
        userRepository.save(user);

        Bottle beverage = new Bottle();
        beverage.setName("Semornare");
        beverage.setPrice(10.00);
        beverage.setInStock(100);
        beverage.setPicture(
                "https://dictionary.cambridge.org/de/images/thumb/bottle_noun_002_04218.jpg?version=6.0.43"
        );
        beverage.setVolume(1.0);
        beverage.setAlcoholic(true);
        beverage.setVolumePercent(5.0);
        beverage.setSupplier("Lacusut");
        beverageRepository.save(beverage);

        OrderItem orderItem = new OrderItem("1", 10.0, beverage, null);
        Order order = new Order(10.0, user, List.of(orderItem));
        orderRepository.save(order);

        billingAddress = new Address("Metusaliquet", "10", "96028");
        Address deliveryAddress = new Address("Nuncvel", "8", "20536");
        addressRepository.save(billingAddress);
        addressRepository.save(deliveryAddress);

        user.setOrders(List.of(order));
        user.setBillingAddresses(Set.of(billingAddress));
        user.setDeliveryAddresses(Set.of(deliveryAddress));

        UserServiceIntegrationTest.user = userRepository.save(user);
    }

    @Test
    public void testLoadByUsername() {
        UserDetails userDetails = userService.findByUsername(user.getUsername());

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
    }

    @Test
    public void testLoadByInvalidUsername() {
        assertThrows(UsernameNotFoundException.class, () -> userService.findByUsername("Anh Molina"));
    }

    @Test
    public void testFindByUsername() {
        User foundUser = userService.findByUsername(user.getUsername());

        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    public void testGetById() {
        User foundUser = userService.getUserById(user.getId());

        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    public void testFindByIdWithOrders() {
        User foundUser = userService.findByUsername(user.getUsername());

        assertNotNull(foundUser);
        assertEquals(user.getOrders(), foundUser.getOrders());
    }

    @Test
    @Transactional
    public void testAddAddressByType() {
        AddressRequestDto addressRequestDto = new AddressRequestDto();
        addressRequestDto.setStreet("Ipsumid");
        addressRequestDto.setNumber("3");
        addressRequestDto.setPostalCode("59635");

        User userWithUpdatedAddresses = userService.addAddressByType(user.getId(), "delivery", addressRequestDto);

        User foundUser = userService.findByUsername(user.getUsername());

        assertEquals(userWithUpdatedAddresses.getDeliveryAddresses(), foundUser.getDeliveryAddresses());
    }

    @Test
    @Transactional
    public void testRemoveAddress() {
        User foundUser = userService.findByUsername(user.getUsername());
        assertEquals(1, foundUser.getBillingAddresses().size());

        userService.removeAddress(billingAddress.getId(), user.getId());

        foundUser = userService.findByUsername(user.getUsername());
        assertEquals(0, foundUser.getBillingAddresses().size());
    }
}
