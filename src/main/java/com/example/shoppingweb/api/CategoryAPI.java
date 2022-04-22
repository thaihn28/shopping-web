package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.service.CategoryService;
import com.example.shoppingweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryAPI {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    // categories
    @RequestMapping("/categories")
    public List<Category> viewAllCategories(){
        return categoryService.getAllCategory();
    }
    @RequestMapping("/category/{id}")
    public List<Product> getProductByCategory(@PathVariable("id") int id) {
        return productService.getAllProductsByCategoryId(id);
    }
}
