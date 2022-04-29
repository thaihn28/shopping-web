package com.example.shoppingweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernameDTO {
    @NotBlank(message = "Username can not be empty")
    private String username;

}