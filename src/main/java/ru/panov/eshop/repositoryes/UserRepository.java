package ru.panov.eshop.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.panov.eshop.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
