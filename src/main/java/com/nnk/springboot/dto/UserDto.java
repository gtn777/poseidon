package com.nnk.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	
	public UserDto() {
		super();
	}

	private Integer id;

	@NotBlank(message = "Username is mandatory.")
	@Size(min = 1, max = 20, message = "Username must be between 2 and 20 characters.")
	private String username;

	@NotBlank(message = "Password is mandatory.")
	@Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters.")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#*$%^&+=]).{8,}$", message = "Password must contains at least one lower alpha char and one upper alpha char, at least one digit, at least one char within a set of special chars (@#%$^ etc.), and Does not contain space, tab, etc.")
	private String password;

	@NotBlank(message = "Fullname is mandatory.")
	@Size(min = 2, max = 30, message = "Fullname must be between 2 and 20 characters.")
	private String fullname;

	@NotBlank(message = "Role is mandatory.")
	@Size(min = 2, max = 30, message = "Role must be between 2 and 20 characters.")
	private String role;
}
