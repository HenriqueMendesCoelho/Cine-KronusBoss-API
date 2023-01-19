package com.kronusboss.cine.adapter.controller.user.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.kronusboss.cine.domain.user.Preferences;
import com.kronusboss.cine.domain.user.Roles;
import com.kronusboss.cine.domain.user.Statistics;
import com.kronusboss.cine.domain.user.User;

import lombok.Data;


@Data
public class UserResponseDto {
	
	private UUID id;
	private String name;
	private String email;
	private String password;
	private Set<String> roles = new HashSet<>();
	private Preferences preferences;
	private Statistics statistics;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public UserResponseDto(User user) {
		id = user.getId();
		name = user.getName();
		email = user.getEmail();
		password = user.getPassword();
		user.getRoles().stream().forEach(r -> addRole(r));
		preferences = user.getPreferences();
		statistics = user.getStatistics();
		createdAt = user.getCreatedAt();
		updatedAt = user.getUpdatedAt();
	}
	
	private void addRole(Roles role) {
		roles.add(role.getDescription());
	}
}
