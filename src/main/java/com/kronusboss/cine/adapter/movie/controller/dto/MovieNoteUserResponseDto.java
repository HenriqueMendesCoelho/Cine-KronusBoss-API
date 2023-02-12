package com.kronusboss.cine.adapter.movie.controller.dto;

import java.util.UUID;

import com.kronusboss.cine.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieNoteUserResponseDto {
	
	private UUID id;
	
	private String name;
	
	public MovieNoteUserResponseDto(User user) {
		id = user.getId();
		name = user.getName();
	}
}
