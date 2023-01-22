package com.kronusboss.cine.usecase.user;

import com.kronusboss.cine.domain.user.User;
import com.kronusboss.cine.usecase.user.exception.DuplicatedUserException;
import com.kronusboss.cine.usecase.user.exception.InviteNotValidException;

public interface CreateUserUseCase {
	
	User save(User user, String inviteCode) throws DuplicatedUserException, InviteNotValidException;
}
