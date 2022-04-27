package com.example.shoppingweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name =  "user", uniqueConstraints = @UniqueConstraint(columnNames = {"username","email", "phoneNo"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Username is required")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotEmpty(message = "Email is required")
    @Column(name = "email")
    private String email;
    @NotNull
    private String phoneNo;
    @NotNull
    private String address;
    private String forgotPasswordToken;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private Instant created;
    @NotNull
    private boolean enabled;



}
