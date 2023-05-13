package com.kronusboss.cine.movie.usecase;

import java.util.UUID;

import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;

public interface DeleteMovieUseCase {

	void delete(UUID id, UUID idUserLoged) throws UserNotAuthorizedException;

}
