package group7.repositories;

import group7.entities.Beverage;
import group7.entities.Bottle;
import group7.entities.Crate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import util.SampleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
public class BeverageRepositoryUnitTest {

    private List<Beverage> invalidBeverages;
    private SampleManager<Beverage> sampleManager;

    @Autowired
    private BeverageRepository beverageRepository;

    @BeforeEach
    public void init() {
        beverageRepository.deleteAll();
        invalidBeverages = new ArrayList<>();
        sampleManager = new SampleManager<>(invalidBeverages);
    }

    @Test
    public void testSavingAndRetrievingValidBeverages() {
        Bottle bottle = getSampleBottle();
        System.out.println(bottle.getSupplier());
        Bottle savedBottle = beverageRepository.save(bottle);

        Crate crate = getSampleCrate(bottle);
        Crate savedCrate = beverageRepository.save(crate);

        Optional<Beverage> retrievedBottle = beverageRepository.findById(savedBottle.getId());
        Optional<Beverage> retrievedCrate = beverageRepository.findById(savedCrate.getId());

        assertThat(retrievedBottle).isPresent();
        assertThat(retrievedCrate).isPresent();
    }

    private Bottle getSampleBottle() {
        return new Bottle(
                "Coke",
                1.0,
                List.of(),
                "https://cdn.huntoffice.co.uk/images/P/ARN10943.jpg",
                1.0,
                false,
                0.0,
                "Coca Cola company",
                1
        );
    }

    private Crate getSampleCrate(Bottle bottle) {
        return new Crate(
                "SampleCrate",
                1.5,
                List.of(),
                "https://t4.ftcdn.net/jpg/03/00/47/33/360_F_300473329_08cy1w5rbmzxLgCaOwgHIYEymVAAJTh9.jpg",
                1,
                1,
                bottle
        );
    }

    private Crate getSampleCrate() {
        return getSampleCrate(null);
    }
}
