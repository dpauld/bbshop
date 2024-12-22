package group7.serviceImpl;

import group7.dto.AddAddressRequestDTO;
import group7.dto.AddressResponseDTO;
import group7.entity.Address;
import group7.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final ModelMapper modelMapper;

    @Autowired
    public AddressServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public Address addAddressRequestDTOTOAddress(AddAddressRequestDTO addAddressRequestDTO) {
        return modelMapper.map(addAddressRequestDTO, Address.class);
    }

    @Override
    public AddressResponseDTO addressToAddressResponseDTO(Address address) {
        return modelMapper.map(address, AddressResponseDTO.class);
    }

    @Override
    public List<Address> addAddressRequestDTOListToAddresses(List<AddAddressRequestDTO> addAddressRequestDTOs) {
        return addAddressRequestDTOs.stream()
                .map(this::addAddressRequestDTOTOAddress)
                .toList();
    }

    @Override
    public List<AddressResponseDTO> addressesToAddressResponseDTOList(List<Address> addresses) {
        return addresses.stream()
                .map(this::addressToAddressResponseDTO)
                .toList();
    }
}
