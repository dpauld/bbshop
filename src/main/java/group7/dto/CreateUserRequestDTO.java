package group7.dto;

import group7.validation.annotation.ValidBirthday;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDTO {

    @NotNull(message = "User name cannot be null")
    @NotEmpty(message = "User name cannot be empty")
    private String username;
    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    @ValidBirthday(message = "Birthday must be after 01.01.1900 and before or on today's date")
    private LocalDate birthday;
    private List<AddAddressRequestDTO> billingAddresses;
    private List<AddAddressRequestDTO> deliveryAddresses;
    private List<CreateOrderRequestDTO> orders;

}
