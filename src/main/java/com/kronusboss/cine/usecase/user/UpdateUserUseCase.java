package com.kronusboss.cine.usecase.user;

import java.util.UUID;

import com.kronusboss.cine.domain.user.User;
import com.kronusboss.cine.usecase.user.exception.UserNotAuthorizedException;
import com.kronusboss.cine.usecase.user.exception.UserNotFoundException;

public interface UpdateUserUseCase {

	User update(User user, UUID id, String emailUserLoged) throws UserNotFoundException, UserNotAuthorizedException;
	
}
