package ru.panov.eshop.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.panov.eshop.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
