package ru.panov.eshop.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.panov.eshop.dto.UserDTO;
import ru.panov.eshop.repositoryes.UserRepository;

@Component
public class UserContainsValidator implements ConstraintValidator<UserContains,UserDTO> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByName(userDTO.getUsername()).isEmpty();
    }
}
