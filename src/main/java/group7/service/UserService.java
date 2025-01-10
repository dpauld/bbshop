package group7.service;

import group7.dto.AddressRequestDto;
import group7.entity.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    User getUserById(Long userId);

    User addAddressByType(Long userId, String type, AddressRequestDto addressRequestDto);

    Boolean removeAddress(Long addressId, Long userId);

    List<User> getAllUsers();

    void save(User user);

    long count();

}