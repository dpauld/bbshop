package group7.controller;

import group7.users.RegistrationForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class RegistrationControllerIntegrationTest {

    private static final String BASE_PATH = "/register";

    @Autowired
    private MockMvc mvc;

    @Test
    public void testRegistrationForm() throws Exception {
        mvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("registrationForm", new RegistrationForm()));
    }

    @Test
    public void testRegistration() throws Exception {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setUsername("testuser");
        registrationForm.setPassword("uij7U33keb4qshy&");
        registrationForm.setFullName("Albert Fu");
        registrationForm.setBirthday(LocalDate.of(2020, 11, 11));

        mvc.perform(createPostRequest(registrationForm).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testRegistrationFailure() throws Exception {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setUsername("user");
        registrationForm.setPassword("uijUkeb4qshy");
        registrationForm.setFullName("Albert Fu");
        registrationForm.setBirthday(LocalDate.of(2049, 11, 11));

        mvc.perform(createPostRequest(registrationForm).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name("register"));
    }

    private MockHttpServletRequestBuilder createPostRequest(RegistrationForm registrationForm) {
        return post(BASE_PATH)
                .param("username", registrationForm.getUsername())
                .param("password", registrationForm.getPassword())
                .param("fullName", registrationForm.getFullName())
                .param("birthday", registrationForm.getBirthday().toString());
    }
}
