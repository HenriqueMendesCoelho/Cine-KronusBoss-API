package com.moviemux.movie.usecase;

import java.util.UUID;

import com.moviemux.movie.domain.Movie;
import com.moviemux.movie.usecase.exception.DuplicatedMovieException;

public interface CreateMovieUseCase {

	Movie save(Movie movie, UUID userId) throws DuplicatedMovieException;
}
