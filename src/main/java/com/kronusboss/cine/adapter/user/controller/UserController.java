package com.kronusboss.cine.adapter.user.controller;

import java.util.List;
import java.util.UUID;

import com.kronusboss.cine.adapter.core.controller.dto.UserTokenDto;
import com.kronusboss.cine.adapter.user.controller.dto.InviteResponseDto;
import com.kronusboss.cine.adapter.user.controller.dto.UserRequestDto;
import com.kronusboss.cine.adapter.user.controller.dto.UserResponseDto;
import com.kronusboss.cine.user.usecase.exception.DuplicatedUserException;
import com.kronusboss.cine.user.usecase.exception.InviteNotValidException;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.user.usecase.exception.UserNotFoundException;

public interface UserController {
	
	List<UserResponseDto> getAllUsers() throws UserNotFoundException;
	
	UserResponseDto getUserByEmail(UserTokenDto user, String email) throws UserNotFoundException, UserNotAuthorizedException;
	
	UserResponseDto createUser(UserRequestDto user) throws DuplicatedUserException, InviteNotValidException;
	
	UserResponseDto update(UserRequestDto user, UUID id, UserTokenDto userLoged) throws UserNotFoundException, UserNotAuthorizedException;
	
	void delete(UUID id);
	
	List<InviteResponseDto> getAllInvites();
	
	InviteResponseDto createUserInvite();
}
