package group7.controller;

import group7.dto.BasketItemDto;
import group7.dto.BeverageResponseDto;
import group7.entity.User;
import group7.service.serviceImpl.BasketServiceImpl;
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
import java.util.Collections;
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
public class BasketControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BasketServiceImpl basketService;

    private User user;

    @BeforeEach
    public void setUp(@Autowired PasswordEncoder passwordEncoder) throws Exception {
        mvc.perform(createRegistrationPostRequest().with(csrf()));
        mvc.perform(createLoginPostRequest().with(csrf()));

        user = getAuthenticatedUser(passwordEncoder, "ROLE_ADMIN");
    }

    @Test
    @Transactional
    public void testViewBasket() throws Exception {
        BeverageResponseDto beverageResponseDto1 = new BeverageResponseDto();
        BeverageResponseDto beverageResponseDto2 = new BeverageResponseDto();

        List<BasketItemDto> items = Arrays.asList(
                new BasketItemDto(beverageResponseDto1, 2),
                new BasketItemDto(beverageResponseDto2, 1)
        );
        when(basketService.getItemsInBasket()).thenReturn(items);
        when(basketService.getTotalPrice()).thenReturn(10.0);

        mvc.perform(get("/basket")
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("basket"))
                .andExpect(model().attribute("beverages", items))
                .andExpect(model().attribute("totalPrice", 10.0));
    }

    @Test
    @Transactional
    public void testViewInvalidBasket() throws Exception {
        List<BasketItemDto> items = Collections.emptyList();
        when(basketService.getItemsInBasket()).thenReturn(items);
        when(basketService.getTotalPrice()).thenReturn(0.0);

        mvc.perform(get("/basket")
                        .with(user(user))
                        .with(csrf())
                        .param("error", "Quantity exceeds available stock.")
                        .param("errorBeverageId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("basket"))
                .andExpect(model().attribute("error", "Quantity exceeds available stock."))
                .andExpect(model().attribute("errorBeverageId", 1L));
    }

    @Test
    @Transactional
    public void testAddBeverageToBasket() throws Exception {
        Long beverageId = 1L;
        doNothing().when(basketService).addItemToBasket(beverageId);

        mvc.perform(post("/basket/add")
                        .with(user(user))
                        .with(csrf())
                        .param("beverageId", String.valueOf(beverageId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/beverages"));
    }

    @Test
    @Transactional
    public void testRemoveBeverageFromBasket() throws Exception {
        Long beverageId = 1L;
        doNothing().when(basketService).removeItemFromBasket(beverageId);

        mvc.perform(post("/basket/remove")
                        .param("beverageId", String.valueOf(beverageId))
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/basket"));
    }

    @Test
    @Transactional
    public void testUpdateBeverageQuantity() throws Exception {
        Long beverageId = 1L;
        int quantity = 2;
        when(basketService.updateItemQuantity(beverageId, quantity)).thenReturn(true);

        mvc.perform(post("/basket/updateQuantity")
                        .param("beverageId", String.valueOf(beverageId))
                        .param("quantity", String.valueOf(quantity))
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/basket"));
    }

    @Test
    @Transactional
    public void testUpdateBeverageWithInvalidQuantity() throws Exception {
        Long beverageId = 1L;
        int quantity = 10;
        when(basketService.updateItemQuantity(beverageId, quantity)).thenReturn(false);

        mvc.perform(post("/basket/updateQuantity")
                        .param("beverageId", String.valueOf(beverageId))
                        .param("quantity", String.valueOf(quantity))
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/basket?error=Quantity+exceeds+available+stock.&errorBeverageId=1"));
    }

    @Test
    @Transactional
    public void testClearBasket() throws Exception {
        doNothing().when(basketService).clearBasket();

        mvc.perform(post("/basket/clear")
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/basket"));
    }
}
