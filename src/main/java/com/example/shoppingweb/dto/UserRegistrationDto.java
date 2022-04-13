package com.example.shoppingweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDto {
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String password;
	private String phoneNo;
	private String address;

	// test merge
}
