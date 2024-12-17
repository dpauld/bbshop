package group7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDTO {

    private String street;
    private String number;
    private String postalCode;
    private UserRequestDTO user;

}
