package com.kronusboss.cine.user.adapter.controller;

import java.util.List;
import java.util.UUID;

import com.kronusboss.cine.adapter.core.controller.dto.UserTokenDto;
import com.kronusboss.cine.user.adapter.controller.dto.InviteResponseDto;
import com.kronusboss.cine.user.adapter.controller.dto.UserRequestDto;
import com.kronusboss.cine.user.adapter.controller.dto.UserResponseAdmDto;
import com.kronusboss.cine.user.adapter.controller.dto.UserResponseDto;
import com.kronusboss.cine.user.usecase.exception.DuplicatedUserException;
import com.kronusboss.cine.user.usecase.exception.InviteNotValidException;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.user.usecase.exception.UserNotFoundException;

public interface UserController {

	List<UserResponseDto> getAllUsers() throws UserNotFoundException;

	UserResponseDto getUserByEmail(UserTokenDto user, String email)
			throws UserNotFoundException, UserNotAuthorizedException;

	UserResponseDto createUser(UserRequestDto user) throws DuplicatedUserException, InviteNotValidException;

	UserResponseDto update(UserRequestDto user, UUID id, UserTokenDto userLoged)
			throws UserNotFoundException, UserNotAuthorizedException;

	UserResponseDto updateUserProfile(UUID userId, String name, String email, boolean notify)
			throws UserNotFoundException;

	UserResponseDto updateUserPassoword(UUID userId, String password, String newPassword)
			throws UserNotFoundException, UserNotAuthorizedException;

	UserResponseAdmDto getUserByEmailAdm(UserTokenDto request, String email)
			throws UserNotFoundException, UserNotAuthorizedException;

	UserResponseAdmDto promoteUserToAdmin(UUID userId) throws UserNotFoundException;

	UserResponseAdmDto demoteUserToAdmin(UUID userId) throws UserNotFoundException;

	UserResponseAdmDto blockUser(UUID userId) throws UserNotFoundException;

	void delete(UUID id);

	List<InviteResponseDto> getAllInvites();

	InviteResponseDto createUserInvite();

	void deleteInvite(String code);

}
