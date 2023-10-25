package ru.panov.eshop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.panov.eshop.dto.BucketDTO;
import ru.panov.eshop.service.BucketService;

import java.security.Principal;

@Controller
@RequestMapping("/bucket")
@AllArgsConstructor
public class BucketController {
    private final BucketService bucketService;

    @GetMapping
    public String getByUser(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("bucket", new BucketDTO());
        }
        model.addAttribute("bucket", bucketService.getByUser(principal.getName()));
        return "bucket";
    }

    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable("id") Long productId, Principal principal) {
        if (principal == null) {
            return "redirect:/";
        }
        bucketService.deleteProduct(principal.getName(), productId);
        return "redirect:/bucket";
    }
}
