package com.kronusboss.cine.usecase.movie;

import com.kronusboss.cine.domain.movie.Movie;

public interface CreateMovieUseCase {
	Movie save(Movie movie);
}
