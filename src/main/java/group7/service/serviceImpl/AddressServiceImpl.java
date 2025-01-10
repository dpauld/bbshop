package group7.service.serviceImpl;

import group7.configuration.customClasses.CustomModelMapper;
import group7.dto.AddressRequestDto;
import group7.entity.Address;
import group7.exception.ResourceNotFoundException;
import group7.repository.AddressRepository;
import group7.repository.OrderRepository;
import group7.repository.UserRepository;
import group7.service.AddressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CustomModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, CustomModelMapper modelMapper, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Address createAddress(AddressRequestDto addressReqDto) {
        Address address = modelMapper.map(addressReqDto, Address.class);
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddressById(Long id, AddressRequestDto addressReqDto) {
        Address address = addressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Address not found"));
        this.modelMapper.map(addressReqDto, address);
        return addressRepository.save(address);
    }

    @Override
    public Address getAddressById(Long id) throws ResourceNotFoundException {
        return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }

    @Override
    public Boolean deleteAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        addressRepository.delete(address);
        return !addressRepository.existsById(id);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

//    public Set<Address> saveAll(Iterable<Address> addresses) {
//        return addressRepository.saveAll(addresses);
//    }

//    public Address saveOrUpdateAddress(Address address, User user, String type) {
//
//        Set<Address> userAddresses = "Billing".equalsIgnoreCase(type) ?
//                user.getBillingAddresses() :
//                user.getDeliveryAddresses();
//
//        // Add or update address
//        if (!userAddresses.contains(address)) {
//            userAddresses.add(address);
//        }
//
//        if (address.getIsDefault()) {
//            // Ensure only one default
//            userAddresses.forEach(addr -> addr.setIsDefault(false));
//            address.setIsDefault(true);
//        }
//
//        return addressRepository.save(address);
//    }

//    public void deleteAddressById(Long addressId, User user, String type) {
//        Set<Address> userAddresses = "Billing".equalsIgnoreCase(type) ?
//                user.getBillingAddresses() :
//                user.getDeliveryAddresses();
//
//        userAddresses.removeIf(addr -> addr.getId().equals(addressId));
//    }
}
