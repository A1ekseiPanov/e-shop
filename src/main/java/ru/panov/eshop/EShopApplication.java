package ru.panov.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EShopApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EShopApplication.class, args);
    }

}
