package ru.panov.eshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import ru.panov.eshop.dto.ProductDTO;
import ru.panov.eshop.model.Product;

public interface ProductService {
    Page<ProductDTO> getProductWithPagingAndFiltering(Specification<Product> specification, int pageNumber);

    void addToUserBucket(Long productId, String username);

    boolean addProduct(ProductDTO dto);

    void remove(Long productId);
}
