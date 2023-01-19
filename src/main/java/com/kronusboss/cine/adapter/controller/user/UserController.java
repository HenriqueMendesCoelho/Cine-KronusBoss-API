package com.kronusboss.cine.adapter.controller.user;

import java.util.UUID;

import com.kronusboss.cine.adapter.controller.user.dto.UserRequestDto;
import com.kronusboss.cine.adapter.controller.user.dto.UserResponseDto;
import com.kronusboss.cine.usecase.user.exception.DuplicatedUserException;
import com.kronusboss.cine.usecase.user.exception.UserNotFoundException;

public interface UserController {
	
	UserResponseDto getUserById(UUID id) throws UserNotFoundException;
	
	UserResponseDto createUser(UserRequestDto user) throws DuplicatedUserException;
}
