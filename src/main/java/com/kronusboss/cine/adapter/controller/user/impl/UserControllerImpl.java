package com.kronusboss.cine.adapter.controller.user.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kronusboss.cine.adapter.controller.core.dto.UserTokenDto;
import com.kronusboss.cine.adapter.controller.user.UserController;
import com.kronusboss.cine.adapter.controller.user.dto.UserRequestDto;
import com.kronusboss.cine.adapter.controller.user.dto.UserResponseDto;
import com.kronusboss.cine.domain.user.User;
import com.kronusboss.cine.usecase.user.CreateUserUseCase;
import com.kronusboss.cine.usecase.user.DeleteUserUseCase;
import com.kronusboss.cine.usecase.user.SearchUserUseCase;
import com.kronusboss.cine.usecase.user.UpdateUserUseCase;
import com.kronusboss.cine.usecase.user.exception.DuplicatedUserException;
import com.kronusboss.cine.usecase.user.exception.InviteNotValidException;
import com.kronusboss.cine.usecase.user.exception.UserNotAuthorizedException;
import com.kronusboss.cine.usecase.user.exception.UserNotFoundException;

@Controller
public class UserControllerImpl implements UserController {

	@Autowired
	private CreateUserUseCase createUserUseCase;

	@Autowired
	private SearchUserUseCase searchUserUseCase;

	@Autowired
	UpdateUserUseCase updateUserUseCase;

	@Autowired
	DeleteUserUseCase deleteUserUseCase;

	@Override
	public List<UserResponseDto> getAllUsers() throws UserNotFoundException {
		return searchUserUseCase.getAllUsers().stream().map(UserResponseDto::new).collect(Collectors.toList());
	}

	@Override
	public UserResponseDto getUserByEmail(UserTokenDto request, String email)
			throws UserNotFoundException, UserNotAuthorizedException {
		User user = searchUserUseCase.getUserByEmail(email, request.getLogin());
		return new UserResponseDto(user);
	}

	@Override
	public UserResponseDto createUser(UserRequestDto request) throws DuplicatedUserException, InviteNotValidException {
		User user = createUserUseCase.save(request.toDomain(), request.getInviteCode());
		return new UserResponseDto(user);
	}

	@Override
	public UserResponseDto update(UserRequestDto request, UUID id, UserTokenDto userLoged)
			throws UserNotFoundException, UserNotAuthorizedException {
		User response = updateUserUseCase.update(request.toDomain(), id, userLoged.getLogin());
		return new UserResponseDto(response);
	}

	@Override
	public void delete(UUID id) {
		deleteUserUseCase.deleteUser(id);
	}

}
