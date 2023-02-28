package com.kronusboss.cine.user.usecase;

import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.user.usecase.exception.DuplicatedUserException;
import com.kronusboss.cine.user.usecase.exception.InviteNotValidException;

public interface CreateUserUseCase {

	User save(User user, String inviteCode) throws DuplicatedUserException, InviteNotValidException;
}
