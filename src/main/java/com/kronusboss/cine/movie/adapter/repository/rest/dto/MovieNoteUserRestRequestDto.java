package com.kronusboss.cine.movie.adapter.repository.rest.dto;

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
public class MovieNoteUserRestRequestDto {

	private UUID id;
	private String name;

	public MovieNoteUserRestRequestDto(User user) {
		id = user.getId();
		name = user.getName();
	}
}
