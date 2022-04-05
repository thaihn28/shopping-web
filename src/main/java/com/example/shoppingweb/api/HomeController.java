package com.example.shoppingweb.api;

import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.repository.ProductRepository;
import com.example.shoppingweb.service.CategoryService;
import com.example.shoppingweb.service.ProductService;
import com.example.shoppingweb.service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

//    @GetMapping("/products")
//    public ResponseEntity<Map<String, Object>> getAllProducts(
//            @RequestParam(value = "order", required = false, defaultValue = "ASC") String order,
//            @RequestParam(value = "orderBy", required = false, defaultValue = "name") String orderBy,
//            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
//            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
//        try {
//            Page<Product> pageRes = productService.getProductsWithPaginationAndSorting(page - 1, pageSize, orderBy, order);
//            Map<String, Object> response = new HashMap<>();
//            response.put("data", pageRes.getContent());
//            response.put("currentPage", pageRes.getNumber());
//            response.put("totalItems", pageRes.getTotalElements());
//            response.put("totalPages", pageRes.getTotalPages());
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/products")
    public List<Product> listProducts(){
        return productRepository.findAll();
    }

    // products
    @GetMapping("/products/{id}")
    public Product productById(@PathVariable("id") Long id){
        try {
            return productService.getProductById(id);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/products/{name}")
    public List<Product> getAllProductByName(@PathVariable("name") String name){
        return productService.getAllProductsByName(name);
    }

    // categories
    @GetMapping("/categories/{id}")
    public List<Product> getProductByCategory(@PathVariable("id") int id){
        return productService.getAllProductsByCategoryId(id);
    }

    @GetMapping("/categories/{name}")
    public List<Category> getAllCategoryByName(@PathVariable("name") String name){
        return categoryService.findAllCategoryByName(name);
    }

}
