package com.example.shoppingweb.controller;

import com.example.shoppingweb.dto.ResetPasswordDTO;
import com.example.shoppingweb.model.User;
import com.example.shoppingweb.repository.OrderRepository;
import com.example.shoppingweb.repository.UserRepository;
import com.example.shoppingweb.service.AuthService;
import com.example.shoppingweb.service.CategoryService;
import com.example.shoppingweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


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
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("orders", orderRepository.findAll().size());
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

    @GetMapping("/forgot-password/{token}")
    public String showForgotPasswordForm(@PathVariable("token") String token, Model model) {
        User user = userRepository.findUserByForgotPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("errmsg", "Invalid Token");
            return "error";
        }

        return "forgot-password-form";
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public String processForgotPasswordForm(HttpServletRequest request,
                                            Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        User user = userRepository.findUserByForgotPasswordToken(token);



        if (user == null) {
            model.addAttribute("errmsg", "Invalid Token");

        } else {
            if (!password.equals(confirmPassword)) {
                model.addAttribute("errmsg", "Password not match");
                return "error";
                
            }
            authService.resetPassword(password, user);

            model.addAttribute("msg", "You have successfully changed your password.");
        }

        return "error";
    }

}
