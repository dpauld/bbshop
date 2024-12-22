package group7.service;

import group7.dto.CreateUserRequestDTO;
import group7.dto.UserResponseDTO;
import group7.entity.User;

public interface UserService {

    public UserResponseDTO registerUser(CreateUserRequestDTO createUserRequestDTO);
    public UserResponseDTO loginUser(String username, String password);
    public UserResponseDTO usertoUserResponseDTO(User user);

    public UserResponseDTO findUserById(Long id);
}
