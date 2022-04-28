package com.example.shoppingweb.controller;


import com.example.shoppingweb.dto.ProductDTO;
import com.example.shoppingweb.model.Category;
import com.example.shoppingweb.model.Discount;
import com.example.shoppingweb.model.Product;
import com.example.shoppingweb.service.CategoryService;
import com.example.shoppingweb.service.DiscountService;
import com.example.shoppingweb.service.ProductService;
import com.example.shoppingweb.service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    DiscountService discountService;

    // Product sessions

    @GetMapping("/products")
    public String getAllPages(Model model){
        model.addAttribute("products", productService.getAllProduct());
//        return "/product/products";
//        Pagination
        return getOnePage(model, 1);
    }

    // Pagination
    @GetMapping("/products/page/{pageNo}")
    public String getOnePage(Model model, @PathVariable(value = "pageNo") int pageNo){
        Page<Product> page = productService.findPaginated(pageNo);
        List<Product> productList = page.getContent();
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("products", productList);

        return "/product/products";
    }
    @GetMapping("/products/page/{pageNo}/{sortField}")
    public String getPageWithSort(Model model,
                                  @PathVariable("pageNo") int pageNo,
                                  @PathVariable String sortField,
                                  @PathParam("sortDir") String sortDir) {

        Page<Product> page = productService.findAllWithSort(sortField, sortDir, pageNo);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);

        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("products", page.getContent());
        return "/product/products";
    }

    // Product detail
    @GetMapping("/products/detail/{id}")
    public String detailProduct(@PathVariable(value = "id") Long id, Model model){
        try {
            Product product = productService.getProductById(id);
            List<Category> categoryList = categoryService.getAllCategory();
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryList);
            model.addAttribute("discounts", discountService.getAllDiscounts());
            return "/product/productDetail";
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return "redirect:/admin/products";
        }
    }

    @GetMapping("/products/add")
    public String addProduct(Model model, RedirectAttributes ra){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("discounts", discountService.getAllDiscounts());
        ra.addFlashAttribute("pageTitle", "Add a new product");
        return "/product/productAdd";
    }

    @PostMapping("/products/add")
    public String saveProduct(@RequestParam(value = "productId",required = false) Long id,
                              @ModelAttribute("productDTO") ProductDTO productDTO, Model model,
                              @RequestParam("productImage") MultipartFile file,
                              @RequestParam("imgName") String imgName,
                              RedirectAttributes ra
    ) throws IOException, UserNotFoundException {
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()));
        product.setDiscount(productDTO.getDiscount());
        product.setPrice(productDTO.getPrice());
        if(productDTO.getDiscount() != null){
            double calDiscount = Double.valueOf(productDTO.getDiscount().getDiscountPrice()) / 100;
            product.setDiscountPrice(productDTO.getPrice() - (calDiscount * productDTO.getPrice()));
        }else {
            product.setDiscountPrice(null);
        }

        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        String imageUUID;

        if(id == null){
            ra.addFlashAttribute("msg", "Added successfully");
        }else {
            ra.addFlashAttribute("msg", "Updated successfully");
        }

        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        }else{
            imageUUID = imgName;
        }
        product.setImageName("http://localhost:8080/productImages/" + imageUUID);
        productService.addProduct(product);
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("discounts", discountService.getAllDiscounts());
        return "redirect:/admin/products";
    }

    @GetMapping("products/delete/{id}")
    public String deleteProductByID(@PathVariable(value = "id") Long id, RedirectAttributes ra){
        productService.deleteProductById(id);
        ra.addFlashAttribute("msg", "Deleted successfully");
        return "redirect:/admin/products";
    }

    @GetMapping("products/update/{id}")
    public String updateProductByID(@PathVariable(value = "id") Long id, Model model){
        try {
            Product product = productService.getProductById(id);

            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(product.getProductId());
            productDTO.setName(product.getName());
            productDTO.setCategoryId(product.getCategory().getId());
//            productDTO.setDiscountId(product.getDiscount().getDiscountId());
            productDTO.setDiscount(product.getDiscount());
            productDTO.setPrice(product.getPrice());
            productDTO.setPriceDiscount(product.getDiscountPrice());
            productDTO.setQuantity(product.getQuantity());
            productDTO.setDescription(product.getDescription());
            productDTO.setImageName(product.getImageName());

            model.addAttribute("categories", categoryService.getAllCategory());
            model.addAttribute("discounts", discountService.getAllDiscounts());
            model.addAttribute("productDTO", productDTO);
            model.addAttribute("pageTitle" , "Update category (Id: " + id + ")" );
            return "/product/productAdd";
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return "redirect:/admin/products";
        }
    }
    @GetMapping("/products/page/{pageNo}/searchProduct")
    public String searchProductByName(@RequestParam(value = "name") String name , Model model, @PathVariable(value = "pageNo") int pageNo){
        Page<Product> page = productService.findAllByName(name, pageNo);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("name", name);
        model.addAttribute("products", page.getContent());
        return "/product/products";
    }
}
