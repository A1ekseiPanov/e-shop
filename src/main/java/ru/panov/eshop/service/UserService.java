package ru.panov.eshop.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.panov.eshop.dto.UserDTO;
import ru.panov.eshop.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean save(UserDTO userDTO);

    void save(User user);

    List<UserDTO> getAll();

    User findByName(String name);

    void updateProfile(UserDTO dto);
}
