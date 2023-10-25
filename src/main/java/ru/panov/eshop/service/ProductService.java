package ru.panov.eshop.service;

import ru.panov.eshop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();

    void addToUserBucket(Long productId, String username);

    boolean addProduct(ProductDTO dto);

    void remove(Long productId);
}
