package com.example.shoppingweb.api;

import com.example.shoppingweb.dto.*;
import com.example.shoppingweb.model.User;
import com.example.shoppingweb.repository.UserRepository;
import com.example.shoppingweb.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/client")
public class ClientController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getLoggedInUser(auth);

        return user;
    }

    @GetMapping("/client-detail")
    public ResponseEntity<?> getClientDetail() {
        User client = getLoggedInUser();
        UserEditDTO userEditDTO = new UserEditDTO();
        userEditDTO.setEmail(client.getEmail());
        userEditDTO.setFirstName(client.getFirstName());
        userEditDTO.setLastName(client.getLastName());
        userEditDTO.setUsername(client.getUsername());
        userEditDTO.setAddress(client.getAddress());
        userEditDTO.setDob(client.getDob());
        userEditDTO.setPhoneNo(client.getPhoneNo());

        return new ResponseEntity<>(userEditDTO, HttpStatus.OK);
    }

    @PostMapping("/edit-username")
    public ResponseEntity<?> editClientUsername(@RequestBody @Valid UsernameDTO usernameDTO) {
        User client = getLoggedInUser();
        if (userRepository.existsByUsername(usernameDTO.getUsername())) {
            return new ResponseEntity<>("Username already exist", HttpStatus.OK);
        }
        client.setUsername(usernameDTO.getUsername());
        userRepository.save(client);
        return new ResponseEntity<>("Save successfully!!", HttpStatus.OK);
    }

    @PostMapping("/edit-client")
    public ResponseEntity<?> editClientDetail(@RequestBody @Valid UserEditDTO userEditDTO) {
        User client = getLoggedInUser();

        client.setFirstName(userEditDTO.getFirstName());
        client.setLastName(userEditDTO.getLastName());
        client.setAddress(userEditDTO.getAddress());

        client.setDob(userEditDTO.getDob());
        client.setPhoneNo(userEditDTO.getPhoneNo());
        userRepository.save(client);

        return new ResponseEntity<>("Save successfully!!", HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePass(@RequestBody @Valid ChangePassDTO changePassDTO) {
        User user = getLoggedInUser();
        if (!bCryptPasswordEncoder.matches(changePassDTO.getOldPassword(), user.getPassword())) {
            return new ResponseEntity<>("Incorrect old password", HttpStatus.BAD_REQUEST);
        }
        if (bCryptPasswordEncoder.matches(changePassDTO.getNewPassword(), user.getPassword())) {
            return new ResponseEntity<>("New password cannot be the same with old password", HttpStatus.BAD_REQUEST);
        }

        if (!changePassDTO.getConfirmPassword().equals(changePassDTO.getNewPassword())) {
            return new ResponseEntity<>("New password not match with confirm password", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(bCryptPasswordEncoder.encode(changePassDTO.getNewPassword()));
        userRepository.save(user);

        return new ResponseEntity<>("Change password successfully", HttpStatus.OK);
    }

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