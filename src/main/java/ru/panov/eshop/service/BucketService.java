package ru.panov.eshop.service;

import ru.panov.eshop.dto.BucketDTO;
import ru.panov.eshop.model.Bucket;
import ru.panov.eshop.model.User;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);

    void addProduct(Bucket bucket, List<Long> productIds);

    BucketDTO getByUser(String username);

    void deleteProduct(String username, Long productId);
}
