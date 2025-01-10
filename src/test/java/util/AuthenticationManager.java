package util;

import group7.entity.User;
import group7.users.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AuthenticationManager {

    private static final RegistrationForm registrationForm;

    static {
        registrationForm = new RegistrationForm();
        registrationForm.setUsername("testuser");
        registrationForm.setPassword("uij7U33keb4qshy&");
        registrationForm.setFullName("Albert Fu");
        registrationForm.setBirthday(LocalDate.of(2020, 11, 11));
    }

    public static MockHttpServletRequestBuilder createLoginPostRequest() {
        return post("/login")
                .param("username", registrationForm.getUsername())
                .param("password", registrationForm.getPassword());
    }

    public static MockHttpServletRequestBuilder createRegistrationPostRequest() {
        return post("/register")
                .param("username", registrationForm.getUsername())
                .param("password", registrationForm.getPassword())
                .param("fullName", registrationForm.getFullName())
                .param("birthday", registrationForm.getBirthday().toString());
    }

    public static User getAuthenticatedUser(PasswordEncoder passwordEncoder, String role) {
        return registrationForm.toUser(passwordEncoder, role);
    }
}
