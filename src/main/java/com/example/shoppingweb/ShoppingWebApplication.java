package com.example.shoppingweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync //enable async processing
public class ShoppingWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingWebApplication.class, args);
    }

}
