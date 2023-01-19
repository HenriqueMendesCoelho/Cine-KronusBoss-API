package com.kronusboss.cine.usecase.user;

import java.util.UUID;

import com.kronusboss.cine.domain.user.User;
import com.kronusboss.cine.usecase.user.exception.UserNotFoundException;

public interface SearchUserUseCase {
	
	User get(UUID id) throws UserNotFoundException;
}
