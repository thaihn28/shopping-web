package com.example.shoppingweb.service;

import com.example.shoppingweb.config.JwtTokenUtil;
import com.example.shoppingweb.dto.ClientLoginRequest;
import com.example.shoppingweb.dto.UserRegistrationDto;
import com.example.shoppingweb.model.NotificationEmail;
import com.example.shoppingweb.model.User;
import com.example.shoppingweb.model.VerificationToken;
import com.example.shoppingweb.repository.UserRepository;
import com.example.shoppingweb.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
//@AllArgsConstructor //use this instead of dependancy injection using autowired (recommended)
public class AuthService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private  VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private  MailService mailService;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    @Qualifier("userDetailsServiceImp")
    private UserDetailsService userDetailsService;


    @Transactional
    public void signup(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        generateVerificationToken(user);
    }
    //verify email by generate a token
    //if user verified -> enabled = true
    private void generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        //click on the url -> check token from db-> fetch the user use this token
        // -> enable
        mailService.sendMail(new NotificationEmail(
                "Welcome to Truong Shopping Web",
                user.getEmail(),
                "Please access this link to verify your account"
                + "http://localhost:8080/api/auth/verify/ " + token
        ));

    }

    public String login(ClientLoginRequest clientLoginRequest) {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
              clientLoginRequest.getUsername(), clientLoginRequest.getPassword()
      ));
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(clientLoginRequest.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);

        return token;
    }
}
