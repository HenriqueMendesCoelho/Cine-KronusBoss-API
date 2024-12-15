package com.moviemux.movie.adapter.controller.dto;

import com.moviemux.movie.domain.MovieGenre;

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
	private Long tmdbId;

	public MovieGenreResponseDto(MovieGenre genre) {
		id = genre.getId();
		name = genre.getName();
		tmdbId = genre.getTmdbId();
	}

}
