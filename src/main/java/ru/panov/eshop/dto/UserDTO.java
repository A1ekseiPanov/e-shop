package ru.panov.eshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.panov.eshop.validator.PasswordMatches;
import ru.panov.eshop.validator.UserContains;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordMatches
@UserContains
public class UserDTO {

    @Size(min = 4, message = "Name must be at least 5 characters long")
    private String username;

    @NotNull
    @Email
    private String email;

    @Size(min = 4, message = "Min size password 4")
    private String password;

    @NotNull
    private String matchingPassword;
}
