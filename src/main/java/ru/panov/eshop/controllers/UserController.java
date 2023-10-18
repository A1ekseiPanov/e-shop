package ru.panov.eshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.panov.eshop.dto.UserDTO;
import ru.panov.eshop.service.UserService;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user";
    }

    @PostMapping("/new")
    public String saveUser(UserDTO dto, Model model) {
        if (userService.save(dto)) {
            return "redirect:/";
        } else {
            model.addAttribute("user", dto);
            return "user";
        }
    }
}
