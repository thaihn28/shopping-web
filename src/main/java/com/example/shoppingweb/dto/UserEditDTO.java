package com.example.shoppingweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String username;
    private String email;
    @Pattern(regexp = "^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$", message = "Must be in Vietnam number format")
    private String phoneNo;
    @NotBlank(message = "Address cannot be empty")
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

}
