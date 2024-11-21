package com.moviemux.user.usecase;

import java.util.List;

import com.moviemux.user.domain.User;
import com.moviemux.user.usecase.exception.UserNotAuthorizedException;
import com.moviemux.user.usecase.exception.UserNotFoundException;

public interface SearchUserUseCase {

	User getUserByEmail(String emailUserToFind, String emailUserLoged)
			throws UserNotFoundException, UserNotAuthorizedException;

	List<User> getAllUsers() throws UserNotFoundException;

}
