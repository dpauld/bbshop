package group7.dto;

import group7.entity.Address;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class AddressRequestDto {
    private Long id;

    @NotEmpty(message = "street can not be empty.")
    @NotNull(message = "street can not be null.")
    @NotBlank(message = "street address is required.")
    private String street;

    @NotEmpty(message = "number can not be empty.")
    @NotNull(message = "number can not be null.")
    @NotBlank(message = "number is required.")
    private String number;

    @NotEmpty(message = "postal code can not be empty.")
    @NotNull(message = "postal code can not be null.")
    @NotBlank(message = "postal code is required.")
    @Size(min = 5, max = 5, message = "postal code should be of 5 character.")
    private String postalCode;
}