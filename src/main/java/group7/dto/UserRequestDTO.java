package group7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

//TODO validations also need to be implemented on dto side after being corrected on entity side ! ! !
    private String username;
    private String password;
    private LocalDate birthday;
    private List<AddressRequestDTO> billingAddresses;
    private List<AddressRequestDTO> deliveryAddresses;
    private List<CreateOrderRequestDTO> orders;

}
