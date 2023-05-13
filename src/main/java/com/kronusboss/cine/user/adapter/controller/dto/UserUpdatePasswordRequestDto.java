package com.kronusboss.cine.user.adapter.controller.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserUpdatePasswordRequestDto {

	@NotBlank
	@Length(min = 8, max = 70)
	private String password;

	@NotBlank
	@Length(min = 8, max = 70)
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,70}$", message = "password is to weak")
	private String newPassword;
}
