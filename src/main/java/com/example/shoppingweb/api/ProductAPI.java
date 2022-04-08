package com.example.shoppingweb.api;

import com.example.shoppingweb.dto.ProductDTO;
import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.repository.CategoryRepository;
import com.example.shoppingweb.repository.ProductRepository;
import com.example.shoppingweb.service.CategoryService;
import com.example.shoppingweb.service.ProductService;
import com.example.shoppingweb.service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductAPI {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;


    @RequestMapping("/products")
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(value = "order", required = false, defaultValue = "ASC") String order,
            @RequestParam(value = "orderBy", required = false, defaultValue = "name") String orderBy,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        try {
            Page<Product> pageRes = productService.getProductsWithPaginationAndSorting(page - 1, pageSize, orderBy, order);
            Map<String, Object> response = new HashMap<>();
            response.put("data", pageRes.getContent());
            response.put("currentPage", pageRes.getNumber());
            response.put("totalItems", pageRes.getTotalElements());
            response.put("totalPages", pageRes.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/add-product")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) throws IOException, UserNotFoundException {
        Product product = new Product();

        product.setProductId(productDTO.getProductId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        product.setImageName(productDTO.getImageName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()));

        productService.addProduct(product);

        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @GetMapping("/product/{id}")
    public Product productById(@PathVariable(value = "id") Long id){
        try {
            return productService.getProductById(id);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    // categories
    @GetMapping("/categories/{id}")
    public List<Product> getProductByCategory(@PathVariable("id") int id){
        return productRepository.findAllByCategoryId(id);
    }

    // categories
    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategory();
    }
}
