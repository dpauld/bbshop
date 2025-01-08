package group7.service2;

import group7.dto.AddressRequestDto;
import group7.entity.Address;

import java.util.List;

public interface AddressService {
    Address createAddress(AddressRequestDto address);
    Address updateAddressById(Long id, AddressRequestDto address);
    Address getAddressById(Long id);
    Boolean deleteAddressById(Long id);
    List<Address> getAllAddresses();
}
