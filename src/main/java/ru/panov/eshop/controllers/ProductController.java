package ru.panov.eshop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.panov.eshop.dto.ProductDTO;
import ru.panov.eshop.service.ProductService;

import java.security.Principal;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("products", productService.getAll());
        System.out.println(productService.getAll());
        return "products";
    }

    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/products";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/products";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new ProductDTO());
        return "product";
    }

    @PostMapping("/new")
    public String addProduct(Model model, ProductDTO dto) {
        if (productService.addProduct(dto)) {
            return "redirect:/products";
        } else {
            model.addAttribute("product", dto);
            return "product";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.remove(id);
        return "redirect:/products";
    }
}
