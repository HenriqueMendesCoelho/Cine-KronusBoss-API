package com.moviemux.user.usecase;

import java.util.UUID;

import com.moviemux.user.domain.User;
import com.moviemux.user.usecase.exception.UserNotAuthorizedException;
import com.moviemux.user.usecase.exception.UserNotFoundException;

public interface UpdateUserUseCase {

	User update(User user, UUID userId, String emailUserLoged) throws UserNotFoundException, UserNotAuthorizedException;

	User updateUserProfile(UUID userId, String name, String email, boolean notify) throws UserNotFoundException;

	User updateUserPassoword(UUID userId, String password, String newPassword)
			throws UserNotFoundException, UserNotAuthorizedException;

	User blockUser(UUID userId) throws UserNotFoundException;

	User promoteUserToAdmin(UUID userId) throws UserNotFoundException;

	User demoteUserToAdmin(UUID userId) throws UserNotFoundException;
}
