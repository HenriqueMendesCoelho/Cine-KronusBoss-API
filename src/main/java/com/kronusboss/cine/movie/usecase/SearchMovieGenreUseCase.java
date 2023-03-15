package com.kronusboss.cine.movie.usecase;

import java.util.List;

import com.kronusboss.cine.movie.domain.MovieGenre;

public interface SearchMovieGenreUseCase {
	List<MovieGenre> list();
}
