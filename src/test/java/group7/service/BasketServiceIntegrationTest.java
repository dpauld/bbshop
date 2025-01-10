package group7.service;

import group7.component.Basket;
import group7.configuration.customClasses.CustomModelMapper;
import group7.dto.BasketItemDto;
import group7.dto.BeverageCreateDto;
import group7.dto.BeverageResponseDto;
import group7.entity.Beverage;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
public class BasketServiceIntegrationTest {

    @Autowired
    private BasketService basketService;

    @Autowired
    private BeverageService beverageService;

    @Autowired
    private CustomModelMapper modelMapper;

    private static Basket basket;

    private Beverage createdBottle1;
    private Beverage createdBottle2;

    @BeforeEach
    public void setUp() {
        basket = basketService.getBasket();
        basketService.clearBasket();

        BeverageCreateDto beverage1 = getSampleBeverage("Maximusnec", 1.0, "Justofeugiat");
        BeverageCreateDto beverage2 = getSampleBeverage("Rutrumultricies", 2.0, "Orcigravida");

        createdBottle1 = beverageService.createBottle(beverage1);
        createdBottle2 = beverageService.createBottle(beverage2);

        basketService.addItemToBasket(createdBottle1.getId());
    }

    private static BeverageCreateDto getSampleBeverage(String name, double volume, String supplier) {
        BeverageCreateDto beverage = new BeverageCreateDto();
        beverage.setName(name);
        beverage.setPrice(10.0);
        beverage.setInStock(10);
        beverage.setPicture(
                "https://dictionary.cambridge.org/de/images/thumb/bottle_noun_002_04218.jpg?version=6.0.43"
        );
        beverage.setVolume(volume);
        beverage.setAlcoholic(false);
        beverage.setVolumePercent(0.0);
        beverage.setSupplier(supplier);
        beverage.setNoOfBottles(1);

        return beverage;
    }

    @Test
    @Transactional
    public void testAddItemToBasket() {
        assertEquals(1, basket.getItems().size());
        assertEquals(createdBottle1.getName(), basket.getItems().get(0).getBeverage().getName());
    }

    @Test
    @Transactional
    public void testUpdateItemQuantity() {
        assertEquals(1, basket.getItems().get(0).getQuantity());

        basketService.updateItemQuantity(createdBottle1.getId(), 3);

        assertEquals(3, basket.getItems().get(0).getQuantity());
    }

    @Test
    @Transactional
    public void testRemoveItemFromBasket() {
        assertEquals(1, basket.getItems().size());
        assertEquals(createdBottle1.getName(), basket.getItems().get(0).getBeverage().getName());

        basketService.removeItemFromBasket(createdBottle1.getId());
        assertEquals(0, basket.getItems().size());
    }

    @Test
    @Transactional
    public void testGetItemsInBasket() {
        basketService.addItemToBasket(createdBottle2.getId());

        List<BasketItemDto> items = basketService.getItemsInBasket();

        assertEquals(2, items.size());

        for (Beverage bottle : List.of(createdBottle1, createdBottle2)) {
            boolean found = false;

            for (BasketItemDto item : items) {
                if (item.getBeverage().getName().equals(bottle.getName())) {
                    found = true;
                    break;
                }
            }

            assertTrue(found);
        }
    }

    @Test
    @Transactional
    public void testCheckout() {
        basketService.addItemToBasket(createdBottle2.getId());

        assertNull(basketService.checkout());
    }

    @Test
    @Transactional
    public void testCheckoutExceedingStock() {
        BeverageResponseDto beverageDto = modelMapper.map(createdBottle1, BeverageResponseDto.class);
        basket.updateQuantity(beverageDto, 20);

        assertEquals(createdBottle1.getId(), basketService.checkout());
    }

    @Test
    @Transactional
    public void testGetTotalPrice() {
        basketService.addItemToBasket(createdBottle2.getId());

        double totalPrice = createdBottle1.getPrice() + createdBottle2.getPrice();

        assertEquals(totalPrice, basketService.getTotalPrice());
    }
}
