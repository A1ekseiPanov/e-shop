package ru.panov.eshop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.panov.eshop.dto.UserDTO;
import ru.panov.eshop.model.User;
import ru.panov.eshop.service.UserService;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileController {
    private UserService userService;

    @GetMapping
    public String getProfile(Model model, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("You not authorize");
        }
        User user = userService.findByName(principal.getName());
        UserDTO dto = UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
        model.addAttribute("user", dto);
        return "profile";
    }

    @PostMapping
    public String updateProfile(UserDTO dto, Model model, Principal principal) {
        if (principal == null || !Objects.equals(principal.getName(), dto.getUsername())) {
            throw new RuntimeException("You not authorize");
        }
        if (dto.getPassword() != null
                && !dto.getPassword().isEmpty()
                && !Objects.equals(dto.getMatchingPassword(), dto.getPassword())) {
            model.addAttribute("user", dto);
            return "profile";
        }
        userService.updateProfile(dto);
        return "redirect:/profile";
    }
}
