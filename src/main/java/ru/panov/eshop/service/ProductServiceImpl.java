package ru.panov.eshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.panov.eshop.dto.ProductDTO;
import ru.panov.eshop.mapper.ProductMapper;
import ru.panov.eshop.model.Bucket;
import ru.panov.eshop.model.Product;
import ru.panov.eshop.model.User;
import ru.panov.eshop.repositoryes.ProductRepository;

import java.util.Collections;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper = ProductMapper.MAPPER;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;

    private final static Integer PAGE_SIZE = 5;

    public ProductServiceImpl(ProductRepository productRepository,
                              UserService userService,
                              BucketService bucketService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
    }

    @Override
    @Transactional
    public void addToUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if (user == null) {
            throw new RuntimeException("User not found - " + username);
        }
        Bucket bucket = user.getBucket();
        if (bucket == null) {
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);
        } else {
            bucketService.addProduct(bucket, Collections.singletonList(productId));
        }
    }

    @Override
    @Transactional
    public boolean addProduct(ProductDTO dto) {
        productRepository.save(productMapper.toProduct(dto));
        return true;
    }

    @Override
    @Transactional
    public void remove(Long productId) {
        Product product = productRepository.getReferenceById(productId);
        if (product == null) {
            throw new RuntimeException(String.format("Product(%s) not found", productId));
        }
        productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> getProductWithPagingAndFiltering(Specification<Product> specification, int pageNumber) {
        Page<Product> products = productRepository.findAll(specification, PageRequest.of(pageNumber-1, PAGE_SIZE));
        Page<ProductDTO> productsDTO = products.map(productMapper::fromProduct);
        return productsDTO;
    }
}