package com.example.shoppingweb.api;

import com.example.shoppingweb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/get-current-user")
    public void getCurrentUser() {
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Name: " + auth.getName() + " Credentials: " + auth.getCredentials() + " Principal: " + auth.getPrincipal());
    }
}
