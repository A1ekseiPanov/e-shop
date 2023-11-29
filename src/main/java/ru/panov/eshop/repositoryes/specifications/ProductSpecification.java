package ru.panov.eshop.repositoryes.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.panov.eshop.model.Product;

import java.math.BigDecimal;

public class ProductSpecification {
        public static Specification<Product> titleContains(String keyword) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), ("%" + keyword + "%").toLowerCase());
        }

        public static Specification<Product> priceGreaterThanOrEq(BigDecimal price) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
        }

        public static Specification<Product> priceLesserThanOrEq(BigDecimal price) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
        }
}