package group7.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {

    private Long id;
    @NotNull(message = "Street cannot be null")
    @NotEmpty(message = "Street cannot be empty")
    private String street;
    @NotNull(message = "Number cannot be null")
    @NotEmpty(message = "Number cannot be empty")
    private String number;
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 5, message = "Size must be exactly 5 digits")
    private String postalCode;
    private UserResponseDTO user;

}
