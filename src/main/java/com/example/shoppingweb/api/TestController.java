package com.example.shoppingweb.api;

import com.example.shoppingweb.model.ItemCartDetail;
import com.example.shoppingweb.model.User;
import com.example.shoppingweb.repository.ItemCartDetailRepository;
import com.example.shoppingweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {

    @Autowired
    ItemCartDetailRepository itemCartDetailRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/test1")
    public ItemCartDetail test() {
    ItemCartDetail b = itemCartDetailRepository.findItemCartDetailByProduct_ProductIdAndUser(3L, userRepository.findUserByUsername("thai"));

        return b;
    }
    @GetMapping("/get-current-user")
    public void getCurrentUser() {
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Name: " + auth.getName() + " Credentials: " + auth.getCredentials() + " Principal: " + auth.getPrincipal());
    }

    @GetMapping("/test-throw")
    public String exceptionTest() {
        throw new TestException("xxxxxxxxxxx");
    }


}
