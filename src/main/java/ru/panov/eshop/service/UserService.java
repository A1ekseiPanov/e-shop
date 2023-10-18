package ru.panov.eshop.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.panov.eshop.dto.UserDTO;

public interface UserService extends UserDetailsService {
    boolean save(UserDTO userDTO);
}
