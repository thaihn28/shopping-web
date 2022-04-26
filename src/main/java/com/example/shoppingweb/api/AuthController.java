package com.example.shoppingweb.api;

import com.example.shoppingweb.dto.*;
import com.example.shoppingweb.model.User;
import com.example.shoppingweb.model.VerificationToken;
import com.example.shoppingweb.repository.UserRepository;
import com.example.shoppingweb.repository.VerificationTokenRepository;
import com.example.shoppingweb.service.AuthService;
import com.example.shoppingweb.service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRegistrationDTO registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return new ResponseEntity<>("New pasword not match with confirm password", HttpStatus.OK);
        }
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return new ResponseEntity<>("Username already exist", HttpStatus.NOT_ACCEPTABLE);
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>("Email already exist", HttpStatus.NOT_ACCEPTABLE);
        }
        authService.signup(registerRequest);

        return new ResponseEntity<>("register successful", HttpStatus.CREATED);

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

    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody UsernameDTO usernameDTO) {
        User user = userRepository.findUserByUsername(usernameDTO.getUsername());
        if (user == null) {
            return new ResponseEntity<>("Username not exist", HttpStatus.NOT_FOUND);
        }
        try {
            authService.generateForgotPasswordToken(usernameDTO.getUsername());
            return new ResponseEntity<>("Reset Password email sent!", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error while sending email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
//    @PostMapping("/reset-password/{token}")
//    public ResponseEntity<?> resetPassword(@PathVariable("token") String token, @RequestBody(required = false) ResetPasswordDTO resetPasswordDTO) {
//        User user = userRepository.findUserByForgotPasswordToken(token);
//        if (user == null) {
//            return new ResponseEntity<>("Token Not Found", HttpStatus.NOT_FOUND);
//        } else {
//            if (resetPasswordDTO == null) {
//                return new ResponseEntity<>(HttpStatus.OK);
//            }
//            if (resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmPassword())) {
//                authService.resetPassword(resetPasswordDTO.getPassword(), user);
//                return new ResponseEntity<>("Password has been changed", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("" +
//                        "", HttpStatus.OK);
//            }
//        }
//    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }




}
