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
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductAPI {
    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @RequestMapping(value = "/products")
    public List<Product> viewAllProducts(){
        return productService.getAllProduct();
    }

    @RequestMapping(value = "product/{id}")
    public Product productById(@PathVariable("id") Long id) throws UserNotFoundException {
        return productService.getProductById(id);
    }

    @RequestMapping(value = "/searchProduct/name/{name}")
    public List<Product> searchProducts(@PathVariable(value = "name", required = false) String keyword){
        return productService.searchingProduct(keyword);
    }

    @RequestMapping(value = "/sortProduct/price/asc")
    public List<Product> sortProductAsc(){
        return productService.sortProductByPriceAsc();
    }
    @RequestMapping(value = "/sortProduct/price/desc")
    public List<Product> sortProductDesc(){
        return productService.sortProductByPriceDesc();
    }

//    @PostMapping("/product/add")
//    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO) throws IOException, UserNotFoundException {
//        Product product = new Product();
//
//        product.setProductId(productDTO.getProductId());
//        product.setName(productDTO.getName());
//        product.setPrice(productDTO.getPrice());
//        product.setQuantity(productDTO.getQuantity());
//        product.setDescription(productDTO.getDescription());
//        product.setImageName(productDTO.getImageName());
//        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()));
//
//        productService.addProduct(product);
//
//        return productDTO;
//    }
//    @PutMapping("/product/update/{id}")
//    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO, @PathVariable(value = "id") Long id) throws IOException, UserNotFoundException {
//        productDTO = new ProductDTO();
//        if(productRepository.existsById(id)){
//            Product product = productService.getProductById(id);
//
//            productDTO.setProductId(product.getProductId());
//            productDTO.setName(product.getName());
//            productDTO.setCategoryId(product.getCategory().getId());
//            productDTO.setPrice(product.getPrice());
//            productDTO.setQuantity(product.getQuantity());
//            productDTO.setDescription(product.getDescription());
//            productDTO.setImageName(product.getImageName());
//
//            productService.addProduct(product);
//            return productDTO;
//        }else {
//            return null;
//        }
//    }
//
//    @GetMapping("/product/{id}")
//    public Product getOneProduct(@PathVariable(value = "id") Long id){
//        try {
//            return productService.getProductById(id);
//        } catch (UserNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @DeleteMapping("product/delete/{id}")
//    public void deleteProduct(@PathVariable(value = "id") Long id) {
//        if (productRepository.existsById(id)) {
//            productService.deleteProductById(id);
//        }
//    }
}
