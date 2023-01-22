package com.kronusboss.cine.usecase.user;

import java.util.List;

import com.kronusboss.cine.domain.user.User;
import com.kronusboss.cine.usecase.user.exception.UserNotAuthorizedException;
import com.kronusboss.cine.usecase.user.exception.UserNotFoundException;

public interface SearchUserUseCase {
	
	User getUserByEmail(String emailUserToFind, String emailUserLoged) throws UserNotFoundException, UserNotAuthorizedException;
	
	List<User> getAllUsers() throws UserNotFoundException;

}
