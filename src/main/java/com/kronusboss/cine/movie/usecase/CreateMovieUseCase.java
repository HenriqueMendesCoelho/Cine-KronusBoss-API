package com.kronusboss.cine.movie.usecase;

import java.util.UUID;

import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieException;

public interface CreateMovieUseCase {

	Movie save(Movie movie, UUID userId) throws DuplicatedMovieException;
}
