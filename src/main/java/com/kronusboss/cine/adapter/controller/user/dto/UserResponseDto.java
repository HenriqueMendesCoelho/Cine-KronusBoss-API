package com.kronusboss.cine.adapter.controller.user.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.kronusboss.cine.domain.user.Role;
import com.kronusboss.cine.domain.user.User;

import lombok.Data;


@Data
public class UserResponseDto {
	
	private UUID id;
	private String name;
	private String email;
	private Set<String> roles = new HashSet<>();
	private PreferencesResponseDto preferences;
	private StatisticsResponseDto statistics;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public UserResponseDto(User user) {
		
		id = user.getId();
		name = user.getName();
		email = user.getEmail();
		user.getRoles().stream().forEach(r -> addRole(r));
		preferences = new PreferencesResponseDto(user.getPreferences());
		statistics = new StatisticsResponseDto(user.getStatistics());
		createdAt = user.getCreatedAt();
		updatedAt = user.getUpdatedAt();
	}
	
	private void addRole(Role role) {
		roles.add(role.getDescription().split("_")[1]);
	}
}
