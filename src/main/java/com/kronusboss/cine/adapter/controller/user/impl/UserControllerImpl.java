package com.kronusboss.cine.adapter.controller.user.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kronusboss.cine.adapter.controller.user.UserController;
import com.kronusboss.cine.adapter.controller.user.dto.UserRequestDto;
import com.kronusboss.cine.adapter.controller.user.dto.UserResponseDto;
import com.kronusboss.cine.domain.user.User;
import com.kronusboss.cine.usecase.user.CreateUserUseCase;
import com.kronusboss.cine.usecase.user.SearchUserUseCase;
import com.kronusboss.cine.usecase.user.exception.DuplicatedUserException;
import com.kronusboss.cine.usecase.user.exception.UserNotFoundException;

@Controller
public class UserControllerImpl implements UserController {
	
	@Autowired
	private CreateUserUseCase createUserUseCase;
	
	@Autowired
	private SearchUserUseCase searchUserUseCase;
	
	@Override
	public UserResponseDto getUserById(UUID id) throws UserNotFoundException {
		User user = searchUserUseCase.get(id);
		return new UserResponseDto(user);
	}

	@Override
	public UserResponseDto createUser(UserRequestDto request) throws DuplicatedUserException {
		User user = createUserUseCase.save(request.toDomain());
		return new UserResponseDto(user);
	}

}
