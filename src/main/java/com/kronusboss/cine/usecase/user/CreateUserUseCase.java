package com.kronusboss.cine.usecase.user;

import com.kronusboss.cine.domain.user.User;
import com.kronusboss.cine.usecase.user.exception.DuplicatedUserException;

public interface CreateUserUseCase {
	
	User save(User user) throws DuplicatedUserException;
}
