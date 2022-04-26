package com.example.shoppingweb.service;

import com.example.shoppingweb.config.jwtconfig.JwtTokenUtil;
import com.example.shoppingweb.dto.ClientLoginRequest;
import com.example.shoppingweb.dto.UserRegistrationDTO;
import com.example.shoppingweb.model.NotificationEmail;
import com.example.shoppingweb.model.User;
import com.example.shoppingweb.model.VerificationToken;
import com.example.shoppingweb.repository.UserRepository;
import com.example.shoppingweb.repository.VerificationTokenRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
//@AllArgsConstructor //use this instead of dependancy injection using autowired (recommended)
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    @Qualifier("userServiceImpl")
    private UserDetailsService userDetailsService;


    @Transactional
    public void signup(UserRegistrationDTO userRegistrationDto) {
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setPhoneNo(userRegistrationDto.getPhoneNo());
        user.setAddress(userRegistrationDto.getAddress());
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
        sendMail(user.getEmail(), token, null);
    }

    @Async
    public void generateForgotPasswordToken(String username) throws UserNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        String token = RandomStringUtils.randomAlphanumeric(20);
        user.setForgotPasswordToken(token);
        sendMail(user.getEmail(), null, token);
        userRepository.save(user);
        try {
            serviceCallB(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    void serviceCallB(User user) throws InterruptedException {
        Thread.sleep(600000); // 10 minutes
        // do service B call stuff
        user.setForgotPasswordToken(null);
        userRepository.save(user);

    }

    public void resetPassword(String pass, User user) {
        user.setPassword(bCryptPasswordEncoder.encode(pass));
        user.setForgotPasswordToken(null);
        userRepository.save(user);
    }

    private void sendMail(String email, String verifyToken, String forgotPassToken) {
        //click on the url -> check token from db-> fetch the user use this token
        // -> enable
        if (verifyToken != null) {
            mailService.sendMail(new NotificationEmail(
                    "Welcome to Truong Shopping Web",
                    email,
                    "Please access this link to verify your account: "
                            + "http://localhost:8080/api/auth/verify/" + verifyToken
            ));
        }
        if (forgotPassToken != null) {
            mailService.sendMail(new NotificationEmail(
                    "Welcome to Truong Shopping Web",
                    email,
                    "Access this link to reset your password: "
                            + "http://localhost:8080/forgot-password/" + forgotPassToken
            ));
        }
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
