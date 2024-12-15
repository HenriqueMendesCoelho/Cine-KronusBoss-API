package com.moviemux.user.usecase;

import com.moviemux.user.domain.User;
import com.moviemux.user.usecase.exception.UserNotAuthorizedException;
import com.moviemux.user.usecase.exception.UserRedefinePasswordKeyInvalid;
import com.moviemux.user.usecase.exception.UserRedefinePasswordKeyNotFound;

public interface UserRedefinePasswordUseCase {

	User redefine(String redefinePasswordKey, String email, String password)
			throws UserRedefinePasswordKeyNotFound, UserRedefinePasswordKeyInvalid, UserNotAuthorizedException;

	void createRedefinePassword(String email);
}
