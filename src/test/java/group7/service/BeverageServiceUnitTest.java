package group7.service;

import group7.dto.BeverageCreateDto;
import group7.dto.BeverageResponseDto;
import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
class BeverageServiceUnitTest {

    @Autowired
    private BeverageService beverageService;

    private static BeverageCreateDto bottle;
    private Beverage createdBottle;

    @BeforeAll
    public static void init() {
        bottle = new BeverageCreateDto();
        bottle.setName("Maximusnec");
        bottle.setPrice(10.0);
        bottle.setInStock(10);
        bottle.setPicture(
                "https://dictionary.cambridge.org/de/images/thumb/bottle_noun_002_04218.jpg?version=6.0.43"
        );
        bottle.setVolume(1.0);
        bottle.setAlcoholic(false);
        bottle.setVolumePercent(0.0);
        bottle.setSupplier("Justofeugiat");
        bottle.setNoOfBottles(1);
    }

    @BeforeEach
    public void setUp() {
        createdBottle = beverageService.createBottle(bottle);
    }

    @Test
    public void testCreateBottle() {
        assertNotNull(createdBottle);
        assertNotNull(beverageService.getBeverageById(createdBottle.getId()));
    }

    @Test
    @Transactional
    public void testCreateCrate() {
        BeverageCreateDto crate = getSampleBeverage();
        Beverage createdCrate = beverageService.createCrate(crate);

        assertNotNull(createdCrate);
        assertNotNull(beverageService.getBeverageById(createdCrate.getId()));
    }

    @Test
    public void testFindBeverageById() {
        BeverageResponseDto response = beverageService.findBeverageById(createdBottle.getId());

        assertNotNull(response);
        assertEquals("Bottle", response.getType());
    }

    @Test
    public void testFindBeverageByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> beverageService.findBeverageById(0L));
    }

    @Test
    public void testUpdate() {
        Bottle bottle = getSampleBottle();
        bottle.setId(createdBottle.getId());

        Beverage updatedBottle = beverageService.update(bottle);

        assertEquals(2.5, updatedBottle.getPrice());
    }

    @Test
    public void testUpdateBeverage() {
        BeverageCreateDto bottleCreateDto = getSampleBeverage();
        bottleCreateDto.setPrice(2.5);

        BeverageResponseDto updatedBottle = beverageService.updateBeverage(bottleCreateDto, createdBottle.getId());

        assertEquals(2.5, updatedBottle.getPrice());
    }

    private BeverageCreateDto getSampleBeverage() {
        BeverageCreateDto beverage = new BeverageCreateDto();
        beverage.setName("Torquentmus");
        beverage.setPrice(50.0);
        beverage.setInStock(10);
        beverage.setPicture(
                "https://dictionary.cambridge.org/de/images/thumb/bottle_noun_002_04218.jpg?version=6.0.43"
        );
        beverage.setVolume(1.0);
        beverage.setAlcoholic(false);
        beverage.setVolumePercent(0.0);
        beverage.setSupplier("Justofeugiat");
        beverage.setNoOfBottles(1);
        beverage.setCratesBottleId(1L);
        beverage.setBottle(new Bottle());

        return beverage;
    }

    private Bottle getSampleBottle() {
        return new Bottle("CocaCola", 2.5, 100, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIxheN74qXKhIgyRTVf_w67JIX4bTmzSvEFQ&s", 1.5, false, 4, "Coca Cola Company");
    }

    @Test
    public void testDeleteBeverage() {
        assertNotNull(beverageService.findBeverageById(createdBottle.getId()));

        assertDoesNotThrow(() -> beverageService.deleteBeverage(createdBottle.getId()));
        assertThrows(ResourceNotFoundException.class, () -> beverageService.findBeverageById(createdBottle.getId()));
    }

    @Test
    public void testDeleteBeverageNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> beverageService.deleteBeverage(0L));
    }
}