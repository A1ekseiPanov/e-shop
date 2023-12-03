package ru.panov.eshop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import ru.panov.eshop.dto.UserDTO;
import ru.panov.eshop.model.Role;
import ru.panov.eshop.model.User;
import ru.panov.eshop.repositoryes.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;

    @Value("${address}")
    private String address;

    public UserServiceImpl(UserRepository userRepository,
                           @Lazy PasswordEncoder passwordEncoder,
                           MailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Override
    @Transactional
    public boolean save(UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .roles(Role.CLIENT)
                .activationCode(UUID.randomUUID().toString())
                .build();

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to E-SHOP. Please, visit next link: %s%s",
                    user.getName(),
                    address,
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByName(username).orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User '%s' not found", username)));

        List<GrantedAuthority> roles = new ArrayList<>();

        roles.add(new SimpleGrantedAuthority(user.getRoles().name()));

        return org.springframework.security.core.userdetails.User.withUsername(user.getName())
                .password(user.getPassword())
                .disabled(!user.isEnabled())
                .authorities(roles)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }

    private UserDTO convertToUserDTO(User user) {
        return UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByName(String name) {
        return userRepository.findByName(name).orElseThrow(
                () -> new RuntimeException(String.format("User '%s' not found", name))
        );
    }

    @Override
    @Transactional
    public void updateProfile(UserDTO dto) {
        User savedUser = findByName(dto.getUsername());

        boolean isChanged = false;

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            savedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            isChanged = true;
        }
        if (!Objects.equals(dto.getEmail(), savedUser.getEmail())) {
            savedUser.setEmail(dto.getEmail());
            isChanged = true;
        }
        if (isChanged) {
            userRepository.save(savedUser);
        }
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setEnabled(true);
        user.setActivationCode(null);
        save(user);
        return true;
    }
}
