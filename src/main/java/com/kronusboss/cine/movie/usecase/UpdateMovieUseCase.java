package com.kronusboss.cine.movie.usecase;

import java.util.UUID;

import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;

public interface UpdateMovieUseCase {
	
	Movie update(Movie movie, UUID id) throws MovieNotFoundException;
}
