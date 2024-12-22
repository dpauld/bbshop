package group7.dto;

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
    private List<AddressResponseDTO> billingAddresses;
    private List<AddressResponseDTO> deliveryAddresses;
    private List<OrderResponseDTO> orders;

}
