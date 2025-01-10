package group7.controller;

import group7.entity.Bottle;
import group7.entity.Crate;
import group7.service.BeverageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class HomeControllerImplUnitTest {

    @MockitoBean
    private BeverageService beverageService;

    @Autowired
    private MockMvc mvc;

    private Bottle beer, wine, bottle1, bottle2;
    private Crate crate1, crate2;
    private List<Bottle> alcoholicBottles;
    private List<Crate> crates;
    private List<Bottle> bottles;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        beer = new Bottle("Beer", 10.00, 100, "https://dictionary.cambridge.org/de/images/thumb/bottle_noun_002_04218.jpg?version=6.0.43", 1.0, true, 15.0, "Tellusper");
        wine = new Bottle("Wine", 10.00, 100, "https://dictionary.cambridge.org/de/images/thumb/bottle_noun_002_04218.jpg?version=6.0.43", 1.0, true, 30.0, "Vehiculabibendum");
        bottle1 = new Bottle("Nullamnunc", 10.00, 100, "https://dictionary.cambridge.org/de/images/thumb/bottle_noun_002_04218.jpg?version=6.0.43", 1.0, false, 0.0, "Efficiturconsectetur");
        bottle2 = new Bottle("Lacuspotenti", 10.00, 100, "https://dictionary.cambridge.org/de/images/thumb/bottle_noun_002_04218.jpg?version=6.0.43", 1.0, false, 0.0, "Namsenectus");
        crate1 = new Crate("Iaculisnisi", 50.00, 10, "https://t4.ftcdn.net/jpg/03/00/47/33/360_F_300473329_08cy1w5rbmzxLgCaOwgHIYEymVAAJTh9.jpg", 6, bottle1);
        crate2 = new Crate("Sempertaciti", 70.00, 10, "https://t4.ftcdn.net/jpg/03/00/47/33/360_F_300473329_08cy1w5rbmzxLgCaOwgHIYEymVAAJTh9.jpg", 8, bottle2);

        alcoholicBottles = List.of(beer, wine);
        crates = List.of(crate1, crate2);
        bottles = List.of(beer, wine, bottle1, bottle2);
    }

    @Test
    public void testHomePage() throws Exception {
        when(beverageService.getAlcoholicBeverages()).thenReturn(List.of(beer, wine));
        when(beverageService.getAllCrates()).thenReturn(List.of(crate1, crate2));
        when(beverageService.getAllBottles()).thenReturn(List.of(beer, wine, bottle1, bottle2));

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("alcoholicBeverages", alcoholicBottles))
                .andExpect(model().attribute("crates", crates))
                .andExpect(model().attribute("bottles", bottles));
    }
}