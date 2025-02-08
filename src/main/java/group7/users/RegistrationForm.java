package group7.users;


import group7.entity.OrderItem;
import group7.entity.User;
import group7.validation.annotation.UniqueEmail;
import group7.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Data
public class RegistrationForm {

    @NotNull
    @Size(min = 8)
    @UniqueUsername
    @Pattern(
            regexp = "^(?!.*[_.]{2})[a-zA-Z][a-zA-Z0-9._]{4,19}(?<![_.])$",
            message = "Username must be 5-20 characters long, start with a letter, and contain only letters, numbers, dots, and underscores (no consecutive dots/underscores)."
    )
    private String username;

    @NotNull
    @UniqueEmail()
    @Pattern(
            regexp="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
            message = "email is not valid"
    )
    @Email(message = "email is not valid")//just @email accepts empty string, so need regex
    private String email;

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
        return new User(username, email, passwordEncoder.encode(password), fullName, birthday, role);
    }
}
