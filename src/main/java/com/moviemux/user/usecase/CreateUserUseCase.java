package com.moviemux.user.usecase;

import com.moviemux.user.domain.User;
import com.moviemux.user.usecase.exception.DuplicatedUserException;
import com.moviemux.user.usecase.exception.InviteNotValidException;

public interface CreateUserUseCase {

	User save(User user, String inviteCode) throws DuplicatedUserException, InviteNotValidException;
}
