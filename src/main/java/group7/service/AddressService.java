package group7.service;

import group7.dto.AddAddressRequestDTO;
import group7.dto.AddressResponseDTO;
import group7.entity.Address;

import java.util.List;

public interface AddressService {

    Address addAddressRequestDTOTOAddress(AddAddressRequestDTO addAddressRequestDTO);
    AddressResponseDTO addressToAddressResponseDTO(Address address);

    List<Address> addAddressRequestDTOListToAddresses(List<AddAddressRequestDTO> addAddressRequestDTOs);
    List<AddressResponseDTO> addressesToAddressResponseDTOList(List<Address> addresses);


}
