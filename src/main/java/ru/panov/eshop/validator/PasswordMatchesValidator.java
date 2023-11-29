package ru.panov.eshop.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ru.panov.eshop.dto.UserDTO;

import java.util.Objects;

@Component
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDTO> {
    @Override
    public boolean isValid(UserDTO o, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.equals(o.getPassword(), o.getMatchingPassword());
    }
}
