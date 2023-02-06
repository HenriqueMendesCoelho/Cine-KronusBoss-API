package com.kronusboss.cine.usecase.movie;

import java.util.UUID;

import com.kronusboss.cine.domain.movie.Movie;
import com.kronusboss.cine.usecase.movie.exception.MovieNotFoundException;

public interface UpdateMovieUseCase {
	Movie update(Movie movie, UUID id) throws MovieNotFoundException;
}
