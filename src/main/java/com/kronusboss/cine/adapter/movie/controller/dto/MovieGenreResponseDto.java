package com.kronusboss.cine.adapter.movie.controller.dto;

import com.kronusboss.cine.movie.domain.MovieGenre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieGenreResponseDto {

	private Long id;
	private String name;

	public MovieGenreResponseDto(MovieGenre genre) {
		id = genre.getId();
		name = genre.getName();
	}

}
