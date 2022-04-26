package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findUserByForgotPasswordToken(String token);
}