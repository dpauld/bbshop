package group7.service;

import group7.dto.BeverageResponseDto;
import group7.dto.PaginatedResponseDto;
import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.entity.Crate;
import group7.repository.BeverageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
class BeverageServiceIntegrationTest {

    @Autowired
    private BeverageRepository beverageRepository;

    @Autowired
    private BasketService basketService;

    @Autowired
    private BeverageService beverageService;

    private Bottle sampleBottle;

    @BeforeEach
    void setUp() {
        beverageRepository.deleteAll();

        sampleBottle = new Bottle();
        sampleBottle.setName("Semornare");
        sampleBottle.setPrice(10.00);
        sampleBottle.setInStock(100);
        sampleBottle.setPicture(
                "https://dictionary.cambridge.org/de/images/thumb/bottle_noun_002_04218.jpg?version=6.0.43"
        );
        sampleBottle.setVolume(1.0);
        sampleBottle.setAlcoholic(true);
        sampleBottle.setVolumePercent(5.0);
        sampleBottle.setSupplier("Lacusut");

        Crate sampleCrate = new Crate();
        sampleCrate.setName("Iaculisnisi");
        sampleCrate.setPrice(50.00);
        sampleCrate.setInStock(10);
        sampleCrate.setPicture(
                "https://t4.ftcdn.net/jpg/03/00/47/33/360_F_300473329_08cy1w5rbmzxLgCaOwgHIYEymVAAJTh9.jpg"
        );
        sampleCrate.setNoOfBottles(6);
        sampleCrate.setBottle(sampleBottle);

        beverageRepository.save(sampleBottle);
        beverageRepository.save(sampleCrate);
    }

    @Test
    void testGetAllBeverages() {
        List<Beverage> result = beverageService.getAllBeverages();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAlcoholicBeverages() {
        List<Beverage> alcoholicBeverages = beverageService.getAlcoholicBeverages();

        assertEquals(1, alcoholicBeverages.size());
        assertEquals(sampleBottle.getName(), alcoholicBeverages.get(0).getName());
    }

    @Test
    void testGetAllBeveragesPaginated() {
        PaginatedResponseDto<BeverageResponseDto> response =
                beverageService.getAllBeveragesPaginated(0, 10, "name", "asc");

        assertNotNull(response);
        assertEquals(2, response.getContent().size());
    }

    @Test
    void testUpdateStock() {
        basketService.addItemToBasket(sampleBottle.getId());

        boolean result = beverageService.updateStock();

        assertTrue(result);
    }
}
