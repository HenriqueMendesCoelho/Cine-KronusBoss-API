package com.moviemux.kronusintegrationtool.adapter.repository.rest.dto;

import com.moviemux.movie.domain.MovieGenre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieGenreResponseDto {

	private Long id;
	private String name;

	public MovieGenre toDomain() {
		return MovieGenre.builder().id(id).name(name).tmdbId(id).build();
	}
}
