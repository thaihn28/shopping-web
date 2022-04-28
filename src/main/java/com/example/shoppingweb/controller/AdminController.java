package com.example.shoppingweb.controller;

import com.example.shoppingweb.repository.OrderRepository;
import com.example.shoppingweb.service.CategoryService;
import com.example.shoppingweb.service.DiscountService;
import com.example.shoppingweb.service.ProductService;
import com.example.shoppingweb.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


// 2 session: Category and Product

@Controller
public class AdminController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    VoucherService voucherService;

    @Autowired
    DiscountService discountService;

    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("orders", orderRepository.findAll().size());
        model.addAttribute("vouchers", voucherService.getAllVouchers());
        model.addAttribute("discounts", discountService.getAllDiscounts());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "accessDenied";
    }

}
