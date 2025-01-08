package group7.users;

import group7.configuration.customClasses.CustomModelMapper;
import group7.dto.AddressRequestDto;
import group7.entity.Address;
import group7.entity.Order;
import group7.exception.ResourceNotFoundException;
import group7.service.AddressService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final ModelMapper modelMapper;
    private UserRepository userRepo;
    private AddressService  addressService;

    @Autowired
    public UserService(UserRepository userRepo, AddressService addressService, CustomModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username);

        if (user != null) {
            return user;
        }

        throw new UsernameNotFoundException("User '" + username + "' not found!");
    }

//    public User updateBillingAddress(Long userId, Address address) {
//        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        Address savedAddress = addressService.createAddress(address);
//        user.getBillingAddresses().add(savedAddress);
//        return userRepo.save(user);
//    }
//
//    public User updateDeliveryAddress(Long userId, Address address) {
//        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        Address savedAddress = addressService.saveAddress(address);
//        user.getDeliveryAddresses().add(savedAddress);
//        return userRepo.save(user);
//    }

    @Transactional
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Transactional
    public User getUserById(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        return user;
    }
    @Transactional
    public User findByIdWithOrders(Long userId) {
        User user = userRepo.findByIdWithOrders(userId);
        return user;
    }

    @Transactional
    public List<Order> getUsersOrderById(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        log.info(user.toString());
        List<Order> orders = user.getOrders();
        return orders;
    }

    //address related
    @Transactional
    public User addAddressByType(Long userId, String type, AddressRequestDto addressRequestDto){
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with id " + userId + " not found"));
        Address address = addressService.createAddress(addressRequestDto);
        log.info(address.toString());
        log.info(type.toString());
        // Handle address based on the type (billing or delivery)
        if ("billing".equalsIgnoreCase(type)) {
            user.getBillingAddresses().add(address); // Add to billing addresses
        } else if ("delivery".equalsIgnoreCase(type)) {
            user.getDeliveryAddresses().add(address); // Add to delivery addresses
        } else {
            throw new IllegalArgumentException("Invalid address type: " + type); // Handle invalid type
        }
        // Save the user with the updated address list
        return userRepo.save(user);
    }

    public User updateAddresses(Long userId, Set<Address> deliveryAddresses, Set<Address> billingAddresses) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setDeliveryAddresses(deliveryAddresses);
        user.setBillingAddresses(billingAddresses);
        return userRepo.save(user);
    }

    public List<Address> getAllAddressesByUserId(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Address> allAddresses = new ArrayList<>();
        allAddresses.addAll(user.getBillingAddresses());
        allAddresses.addAll(user.getDeliveryAddresses());
        return allAddresses;
    }

    public Boolean removeAddress(Long addressId, Long userId) {
        Address address = addressService.getAddressById(addressId);

        //breaking the relationship between address and user
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getBillingAddresses().contains(address)) {
            user.getBillingAddresses().remove(address);
        } else if (user.getDeliveryAddresses().contains(address)) {
            user.getDeliveryAddresses().remove(address);
        } else {
            throw new IllegalArgumentException("Address does not belong to the user.");
        }
        userRepo.save(user);

        //directly only excuting it also breaks the relationship, above checking for safety
        addressService.deleteAddressById(addressId);//removes the address from address table.

        return !user.getBillingAddresses().contains(address) && !user.getDeliveryAddresses().contains(address);
    }
}
