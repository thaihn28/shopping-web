package com.example.shoppingweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassDTO {
    @NotBlank(message = "Please fill in your old password to verify")
    private String oldPassword;
    @NotBlank(message = "New password cannot be empty")
    private String newPassword;
    @NotBlank(message = "Confirm password cannot be empty")
    private String confirmPassword;
}