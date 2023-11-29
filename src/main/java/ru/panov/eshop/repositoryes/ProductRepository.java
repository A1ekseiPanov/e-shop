package ru.panov.eshop.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.panov.eshop.model.Product;
@Repository
public interface ProductRepository extends JpaSpecificationExecutor<Product>, JpaRepository<Product,Long>{

}
