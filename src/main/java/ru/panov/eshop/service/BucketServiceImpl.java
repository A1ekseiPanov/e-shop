package ru.panov.eshop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.panov.eshop.dto.BucketDTO;
import ru.panov.eshop.dto.BucketDetailDTO;
import ru.panov.eshop.model.Bucket;
import ru.panov.eshop.model.OrderDetails;
import ru.panov.eshop.model.Product;
import ru.panov.eshop.model.User;
import ru.panov.eshop.repositoryes.BucketRepository;
import ru.panov.eshop.repositoryes.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BucketServiceImpl implements BucketService {
    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Bucket createBucket(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> products = getRefProdByIds(productIds);
        bucket.setProducts(products);
        return bucketRepository.save(bucket);
    }

    private List<Product> getRefProdByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getReferenceById)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addProduct(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProducts = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProducts.addAll(getRefProdByIds(productIds));
        bucket.setProducts(newProducts);
        bucketRepository.save(bucket);
    }

    @Override
    @Transactional(readOnly = true)
    public BucketDTO getByUser(String username) {
        User user = userService.findByName(username);
        if (user == null || user.getBucket() == null) {
            return new BucketDTO();
        }
        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailDTO> mapByProductId = new HashMap<>();
        List<Product> products = user.getBucket().getProducts();
        for (Product product : products) {
            BucketDetailDTO detail = mapByProductId.get(product.getId());
            if (detail == null) {
                mapByProductId.put(product.getId(), new BucketDetailDTO(product));
            } else {
                detail.setAmount(detail.getAmount().add(new BigDecimal(1)));
                detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }
        bucketDTO.setBucketDetail(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate();
        return bucketDTO;
    }

    @Override
    @Transactional
    public void deleteProduct(String username, Long productId) {
        User user = userService.findByName(username);
        if (user == null) {
            throw new RuntimeException("User not found - " + username);
        }
        List<Product> products = user.getBucket().getProducts();
        products.removeIf(product -> product.getId().equals(productId));
        userService.save(user);
    }
}
