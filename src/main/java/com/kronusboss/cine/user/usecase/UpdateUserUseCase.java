package com.kronusboss.cine.user.usecase;

import java.util.UUID;

import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.user.usecase.exception.UserNotFoundException;

public interface UpdateUserUseCase {

	User update(User user, UUID id, String emailUserLoged) throws UserNotFoundException, UserNotAuthorizedException;
	
}
