package com.moviemux.movie.usecase;

import java.util.UUID;

import com.moviemux.movie.domain.Movie;
import com.moviemux.movie.usecase.exception.MovieNotFoundException;
import com.moviemux.user.usecase.exception.UserNotAuthorizedException;

public interface UpdateMovieUseCase {

	Movie update(Movie movie, UUID id, String userEmail) throws MovieNotFoundException, UserNotAuthorizedException;
}
