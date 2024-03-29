package ru.panov.eshop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.panov.eshop.dto.ProductDTO;
import ru.panov.eshop.model.Product;
import ru.panov.eshop.repositoryes.specifications.ProductSpecification;
import ru.panov.eshop.service.ProductService;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "min_price", required = false) BigDecimal minPrice,
                         @RequestParam(value = "max_price", required = false) BigDecimal maxPrice) {
        return getAllByPage(model, keyword, minPrice, maxPrice, 1);
    }

    @GetMapping("/page/{page}")
    public String getAllByPage(Model model,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "min_price", required = false) BigDecimal minPrice,
                               @RequestParam(value = "max_price", required = false) BigDecimal maxPrice,
                               @PathVariable("page") Integer currentPage) {
        if (currentPage == null) {
            currentPage = 1;
        }
        Specification<Product> specification = Specification.where(null);
        if (keyword != null) {
            specification = specification.and(ProductSpecification.titleContains(keyword));
        }
        if (minPrice != null) {

            specification = specification.and(ProductSpecification.priceGreaterThanOrEq(minPrice));
        }
        if (maxPrice != null) {

            specification = specification.and(ProductSpecification.priceLesserThanOrEq(maxPrice));
        }
        Page<ProductDTO> p = productService.getProductWithPagingAndFiltering(specification, currentPage);
        int totalPages = p.getTotalPages();
        if (totalPages == 0) {
            totalPages = 1;
        }
        long totalItems = p.getTotalElements();
        model.addAttribute("key", keyword);
        model.addAttribute("min", minPrice);
        model.addAttribute("max", maxPrice);
        model.addAttribute("page", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("productDTOList", p);
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
