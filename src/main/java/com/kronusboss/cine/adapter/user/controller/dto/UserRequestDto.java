package com.kronusboss.cine.adapter.user.controller.dto;

import org.hibernate.validator.constraints.Length;

import com.kronusboss.cine.user.domain.Preferences;
import com.kronusboss.cine.user.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
	
	@NotBlank
	@Length(max = 100)
	@Length(min = 3)
	private String name;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Length(min = 8)
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
