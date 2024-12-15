package com.moviemux.wishlist.adapter.controller.dto;

import java.util.List;

import com.moviemux.movie.domain.Movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MoviesAlreadyRatedResponseDto {

	private List<Long> movieTmdbIds;

	public MoviesAlreadyRatedResponseDto(List<Movie> movies) {
		movieTmdbIds = movies.stream().map(m -> m.getTmdbId()).toList();
	}
}
