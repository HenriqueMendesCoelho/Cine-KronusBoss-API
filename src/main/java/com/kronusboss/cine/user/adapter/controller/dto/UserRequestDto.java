package com.kronusboss.cine.user.adapter.controller.dto;

import org.hibernate.validator.constraints.Length;

import com.kronusboss.cine.user.domain.Preferences;
import com.kronusboss.cine.user.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequestDto {

	@NotBlank
	@Length(min = 3, max = 100)
	private String name;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Length(min = 8, max = 70)
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,70}$", message = "password is to weak")
	private String password;

	private boolean notify;

	private String inviteCode;

	public User toDomain() {
		return User.builder()
				.name(name)
				.email(email)
				.password(password)
				.preferences(Preferences.builder().notify(notify).build())
				.build();
	}
}
