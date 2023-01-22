package com.kronusboss.cine.adapter.controller.user;

import java.util.List;
import java.util.UUID;

import com.kronusboss.cine.adapter.controller.core.dto.UserTokenDto;
import com.kronusboss.cine.adapter.controller.user.dto.UserRequestDto;
import com.kronusboss.cine.adapter.controller.user.dto.UserResponseDto;
import com.kronusboss.cine.usecase.user.exception.DuplicatedUserException;
import com.kronusboss.cine.usecase.user.exception.InviteNotValidException;
import com.kronusboss.cine.usecase.user.exception.UserNotAuthorizedException;
import com.kronusboss.cine.usecase.user.exception.UserNotFoundException;

public interface UserController {
	
	List<UserResponseDto> getAllUsers() throws UserNotFoundException;
	
	UserResponseDto getUserByEmail(UserTokenDto user, String email) throws UserNotFoundException, UserNotAuthorizedException;
	
	UserResponseDto createUser(UserRequestDto user) throws DuplicatedUserException, InviteNotValidException;
	
	UserResponseDto update(UserRequestDto user, UUID id, UserTokenDto userLoged) throws UserNotFoundException, UserNotAuthorizedException;
	
	void delete(UUID id);
}
