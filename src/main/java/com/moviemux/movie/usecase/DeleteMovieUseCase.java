package com.moviemux.movie.usecase;

import java.util.UUID;

import com.moviemux.user.usecase.exception.UserNotAuthorizedException;

public interface DeleteMovieUseCase {

	void delete(UUID id, UUID idUserLoged) throws UserNotAuthorizedException;

}
