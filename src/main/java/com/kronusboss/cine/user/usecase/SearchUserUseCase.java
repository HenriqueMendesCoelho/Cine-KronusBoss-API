package com.kronusboss.cine.user.usecase;

import java.util.List;

import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.user.usecase.exception.UserNotFoundException;

public interface SearchUserUseCase {

	User getUserByEmail(String emailUserToFind, String emailUserLoged)
			throws UserNotFoundException, UserNotAuthorizedException;

	List<User> getAllUsers() throws UserNotFoundException;

}
