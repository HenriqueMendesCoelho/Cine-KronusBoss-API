package com.kronusboss.cine.usecase.movie;

import com.kronusboss.cine.domain.movie.Movie;
import com.kronusboss.cine.usecase.movie.exception.DuplicatedMovieException;

public interface CreateMovieUseCase {
	Movie save(Movie movie) throws DuplicatedMovieException;
}
