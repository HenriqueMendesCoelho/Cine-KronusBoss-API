package com.moviemux.movie.adapter.controller.dto;

import java.util.UUID;

import com.moviemux.user.domain.User;

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
