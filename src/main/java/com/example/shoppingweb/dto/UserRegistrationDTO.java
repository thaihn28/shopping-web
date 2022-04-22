package com.example.shoppingweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDTO {
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank(message = "Username can not be empty")
	private String username;
	@Email(message = "Must be email formatted")
	@NotBlank(message = "Email can not be empty")
	private String email;
	@NotBlank(message = "Password can not be empty")
	@Size(min = 5, message = "Password length must greater than 5")
	private String password;
	@NotBlank(message = "Confirm password can not be empty")
	private String confirmPassword;
	@NotBlank(message = "Phone number can not be empty")
	@Length(max = 10, min = 10)
	@Pattern(regexp = "^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$", message = "Must be in Vietnam number format")
	private String phoneNo;
	@NotBlank(message = "Address cannot be empty")
	private String address;

	// test merge
}
