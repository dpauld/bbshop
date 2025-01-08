package group7.users;


import group7.validation.annotation.UniqueUsername;
import group7.validation.annotation.ValidBirthday;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Data
public class RegistrationForm {

    @NotNull
    @Size(min = 8)
    //@UniqueUsername
    @Pattern(regexp = "[a-z]*", message = "username only contains at least 8 lower case letters")
    private String username;

    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "at least 8 chars, containing digits, lower and upper case letters and special characters")
    private String password;

    @NotNull
    @NotEmpty
    @NotBlank
    private String fullName;

    //@ValidBirthday(message = "Birthday must be after 01.01.1900 and before or on today's date")
    //@Past(message = "Birthday must be in the past")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthday;

    public User toUser(PasswordEncoder passwordEncoder, String role) {
        return new User(username, passwordEncoder.encode(password), fullName, birthday, role);
    }
}
