package com.kronusboss.cine.movie.usecase;

import java.util.UUID;

import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;

public interface UpdateMovieUseCase {

	Movie update(Movie movie, UUID id, String userEmail) throws MovieNotFoundException, UserNotAuthorizedException;
}
