package ru.panov.eshop.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.panov.eshop.model.Bucket;

public interface BucketRepository extends JpaRepository<Bucket,Long> {
}
