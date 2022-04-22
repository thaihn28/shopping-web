package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
