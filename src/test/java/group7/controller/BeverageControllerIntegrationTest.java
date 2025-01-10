package group7.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static util.AuthenticationManager.*;

import group7.dto.BeverageCreateDto;
import group7.dto.BeverageResponseDto;
import group7.dto.PaginatedResponseDto;
import group7.entity.Bottle;
import group7.entity.Crate;
import group7.entity.User;
import group7.service.BeverageService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class BeverageControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BeverageService beverageService;

    private User user;

    @BeforeEach
    public void setUp(@Autowired PasswordEncoder passwordEncoder) throws Exception {
        mvc.perform(createRegistrationPostRequest().with(csrf()));
        mvc.perform(createLoginPostRequest().with(csrf()));

        user = getAuthenticatedUser(passwordEncoder, "ROLE_ADMIN");
    }

    @Test
    @Transactional
    public void testCreateBottle() throws Exception {
        BeverageCreateDto beverageCreateDto = new BeverageCreateDto();
        when(beverageService.createBottle(any())).thenReturn(new Bottle());

        mvc.perform(post("/beverages/bottles")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", beverageCreateDto.getName())
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/beverages"));
    }

    @Test
    @Transactional
    public void testCreateCrate() throws Exception {
        BeverageCreateDto beverageCreateDto = new BeverageCreateDto();
        when(beverageService.createCrate(any())).thenReturn(new Crate());

        mvc.perform(post("/beverages/crates")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", beverageCreateDto.getName())
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bevereges"));
    }

    @Test
    @Transactional
    public void testGetAllBeveragesPaginated() throws Exception {
        PaginatedResponseDto<BeverageResponseDto> paginatedResponseDto = new PaginatedResponseDto<>();
        paginatedResponseDto.setContent(Arrays.asList(new BeverageResponseDto(), new BeverageResponseDto()));
        paginatedResponseDto.setPageNumber(0);
        paginatedResponseDto.setPageSize(10);
        paginatedResponseDto.setTotalPages(1);
        paginatedResponseDto.setLastPage(true);

        when(beverageService.getAllBeveragesPaginated(0, 10, "name", "asc")).thenReturn(paginatedResponseDto);

        mvc.perform(get("/beverages")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sort", "name")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(view().name("bevereges"))
                .andExpect(model().attributeExists("beverages", "currentPage", "pageSize", "sortBy", "totalPages", "isLastPage"));
    }

    @Test
    @Transactional
    public void testGetBeverageById() throws Exception {
        BeverageResponseDto beverage = new BeverageResponseDto();
        beverage.setId(1L);
        when(beverageService.findBeverageById(1L)).thenReturn(beverage);

        mvc.perform(get("/beverages/1")
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("beverage"))
                .andExpect(model().attribute("beverage", beverage));
    }

    @Test
    @Transactional
    public void testGetAllBottlesJson() throws Exception {
        Bottle bottle1 = new Bottle();
        bottle1.setId(1L);
        Bottle bottle2 = new Bottle();
        bottle2.setId(2L);

        List<Bottle> bottles = Arrays.asList(bottle1, bottle2);
        when(beverageService.getAllBottles()).thenReturn(bottles);

        mvc.perform(get("/beverages/bottles/json")
                        .with(user(user))
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    public void testGetAllCratesJson() throws Exception {
        Crate crate1 = new Crate();
        crate1.setId(1L);
        Crate crate2 = new Crate();
        crate2.setId(2L);

        List<Crate> crates = Arrays.asList(crate1, crate2);
        when(beverageService.getAllCrates()).thenReturn(crates);

        mvc.perform(get("/beverages/crates/json")
                        .with(user(user))
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    public void testShowUpdateForm() throws Exception {
        BeverageResponseDto beverage = new BeverageResponseDto();
        beverage.setId(1L);
        when(beverageService.findBeverageById(1L)).thenReturn(beverage);

        mvc.perform(get("/beverages/update/1")
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("beverage"))
                .andExpect(view().name("beverageUpdateForm"));
    }

    @Test
    @Transactional
    public void testUpdateBottle() throws Exception {
        BeverageCreateDto beverageCreateDto = new BeverageCreateDto();
        BeverageResponseDto updatedResponseDto = new BeverageResponseDto();
        updatedResponseDto.setId(1L);

        when(beverageService.updateBeverage(beverageCreateDto, 1L)).thenReturn(updatedResponseDto);

        mvc.perform(put("/beverages/bottles/{id}", 1L, beverageCreateDto)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/beverages"));
    }

    @Test
    @Transactional
    public void testUpdateCrate() throws Exception {
        BeverageCreateDto beverageCreateDto = new BeverageCreateDto();
        BeverageResponseDto updatedResponseDto = new BeverageResponseDto();

        when(beverageService.updateBeverage(beverageCreateDto, 1L)).thenReturn(updatedResponseDto);

        mvc.perform(put("/beverages/crates/{id}", 1L, beverageCreateDto)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/beverages"));
    }

    @Test
    @Transactional
    public void testDeleteBeverage() throws Exception {
        doNothing().when(beverageService).deleteBeverage(1L);

        mvc.perform(delete("/beverages/1")
                        .with(user(user))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
