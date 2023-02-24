package com.kronusboss.cine.adapter.movie.controller.dto;

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

	private String name;

	public MovieNoteUserResponseDto(User user) {
		name = user.getName();
	}
}
