package group7.service.impl;

import group7.dto.*;
import group7.entity.Address;
import group7.entity.Order;
import group7.entity.User;
import group7.repository.UserRepository;
import group7.service.AddressService;
import group7.service.OrderService;
import group7.service.UserService;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AddressService addressService, OrderService orderService, @Qualifier("modelMapper") ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public UserResponseDTO registerUser(CreateUserRequestDTO createUserRequestDTO) {
        List<Address> billingAddresses = addressService.addAddressRequestDTOListToAddresses(createUserRequestDTO.getBillingAddresses());
        List<Address> deliveryAddresses = addressService.addAddressRequestDTOListToAddresses(createUserRequestDTO.getDeliveryAddresses());
        List<Order> orders = orderService.createOrderRequestDTOListToOrders(createUserRequestDTO.getOrders());
        User user = new User(createUserRequestDTO.getUsername(),
                createUserRequestDTO.getPassword(),
                createUserRequestDTO.getBirthday(),
                billingAddresses,
                deliveryAddresses,
                orders);

        User savedUser = userRepository.save(user);

        List<AddressResponseDTO> billingAddressesResponse = addressService.addressesToAddressResponseDTOList(billingAddresses);
        List<AddressResponseDTO> deliveryAddressesResponse = addressService.addressesToAddressResponseDTOList(deliveryAddresses);
        List<OrderResponseDTO> orderResponseDTOList = orderService.ordersToOrderResponseDTOList(orders);

        return new UserResponseDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getBirthday(), billingAddressesResponse, deliveryAddressesResponse, orderResponseDTOList);
    }

    @Override
    public UserResponseDTO usertoUserResponseDTO(User user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO findUserById(Long id) {
        return userRepository.findById(id)
                .map(this::usertoUserResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional
    @Override
    public UserResponseDTO loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return usertoUserResponseDTO(user);
        }
        else {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }


}
