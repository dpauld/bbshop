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
public class UserResponseDTO {

    private Long id;
    @NotNull(message = "User name cannot be null")
    @NotEmpty(message = "User name cannot be empty")
    private String username;
    @ValidBirthday(message = "Birthday must be after 01.01.1900 and before or on today's date")
    private LocalDate birthday;
    private List<AddressResponseDTO> billingAddresses;
    private List<AddressResponseDTO> deliveryAddresses;
    private List<OrderResponseDTO> orders;


}
