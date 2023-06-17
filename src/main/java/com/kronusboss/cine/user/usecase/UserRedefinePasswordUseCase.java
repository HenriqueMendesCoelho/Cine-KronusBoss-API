package com.kronusboss.cine.user.usecase;

import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.user.usecase.exception.UserRedefinePasswordKeyInvalid;
import com.kronusboss.cine.user.usecase.exception.UserRedefinePasswordKeyNotFound;

public interface UserRedefinePasswordUseCase {

	User redefine(String redefinePasswordKey, String email, String password)
			throws UserRedefinePasswordKeyNotFound, UserRedefinePasswordKeyInvalid, UserNotAuthorizedException;

	void createRedefinePassword(String email);
}
