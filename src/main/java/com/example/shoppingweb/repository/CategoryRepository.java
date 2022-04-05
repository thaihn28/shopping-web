package com.example.shoppingweb.repository;

import com.example.shoppingweb.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByNameContains(String name);
}
