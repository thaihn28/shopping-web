package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Order;
import com.example.shoppingweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getAllByUser(User user);
    Order getOrderByIdAndUser(Long id, User user);
    boolean existsByUserAndAndId(User user, Long id);
}
