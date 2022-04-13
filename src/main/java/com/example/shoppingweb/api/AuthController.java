package com.example.shoppingweb.api;

import com.example.shoppingweb.dto.ClientLoginRequest;
import com.example.shoppingweb.dto.JwtResponse;
import com.example.shoppingweb.dto.UserRegistrationDto;
import com.example.shoppingweb.model.User;
import com.example.shoppingweb.model.VerificationToken;
import com.example.shoppingweb.repository.UserRepository;
import com.example.shoppingweb.repository.VerificationTokenRepository;
import com.example.shoppingweb.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/test2")
    public void test2() throws Exception {
        throw new Exception("USER_DISABLED");
    }
    @PostMapping("/test3")
    public String test3() {
        return "Welcome";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserRegistrationDto registerRequest) {
        authService.signup(registerRequest);

        return new ResponseEntity<>("register successful", HttpStatus.OK);

    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable("token") String token) {
        VerificationToken tokenObj = verificationTokenRepository.findVerificationTokenByToken(token);
        if (tokenObj == null || tokenObj.getToken().isEmpty()) {
            return new ResponseEntity<>("Token Not Found", HttpStatus.NOT_FOUND);
        }
        User user = tokenObj.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        return new ResponseEntity<>("Your account has been verified", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ClientLoginRequest clientLoginRequest) {
        String token = authService.login(clientLoginRequest);

        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }
}
