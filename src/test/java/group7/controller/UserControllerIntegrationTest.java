package group7.controller;

import group7.entity.User;
import group7.repository.UserRepository;
import group7.users.RegistrationForm;
import group7.users.UserUpdateForm;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerIntegrationTest {

    private static final String BASE_PATH = "/users";

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserRepository userRepository;

    private User user;
    private User admin;

    @BeforeEach
    public void setUp(@Autowired PasswordEncoder passwordEncoder) {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setUsername("User 1");
        registrationForm.setPassword("Ir0bn2Wk8T8K1U");
        registrationForm.setFullName("Eva Ning");
        registrationForm.setBirthday(LocalDate.of(2001, 5, 16));
        user = registrationForm.toUser(passwordEncoder, "ROLE_USER");
        admin = registrationForm.toUser(passwordEncoder, "ROLE_ADMIN");

        when(userRepository.findAll()).thenReturn(List.of(user, admin));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attribute("users", List.of(user, admin)))
                .andExpect(model().attribute("roles", Arrays.asList("ROLE_ADMIN", "ROLE_USER")))
                .andExpect(model().attribute("userUpdateForm", new UserUpdateForm()));
    }

    @Test
    public void testChangeRightFromUserToAdmin() throws Exception {
        UserUpdateForm userUpdateForm = new UserUpdateForm();
        userUpdateForm.setNewRole("ROLE_ADMIN");

        mvc.perform(createPostRequest(user.getUsername(), userUpdateForm)
                        .with(user(admin))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("users"));
    }

    @Test
    public void testChangeRightFromAdminToUser() throws Exception {
        UserUpdateForm userUpdateForm = new UserUpdateForm();
        userUpdateForm.setNewRole("ROLE_USER");

        mvc.perform(createPostRequest(admin.getUsername(), userUpdateForm)
                        .with(user(admin))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("users"));
    }

    private MockHttpServletRequestBuilder createPostRequest(String username, UserUpdateForm updateForm) {
        return post(BASE_PATH)
                .param("username", username)
                .param("role", updateForm.getNewRole());
    }
}
