package com.kronusboss.cine.user.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kronusboss.cine.adapter.user.repository.jpa.UserRepository;
import com.kronusboss.cine.user.domain.Role;
import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.user.usecase.UpdateUserUseCase;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.user.usecase.exception.UserNotFoundException;

@Service
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

	@Autowired
	UserRepository repository;

	@Override
	public User update(User user, UUID id, String emailUserLoged)
			throws UserNotFoundException, UserNotAuthorizedException {
		User userLoged = repository.findByEmail(emailUserLoged);
		User userToUpdate = repository.getReferenceById(id);

		if (userLoged.getId() != id && !userLoged.getRoles().contains(Role.ADM)) {
			throw new UserNotAuthorizedException();
		}

		if (userToUpdate == null) {
			throw new UserNotFoundException();
		}

		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setName(user.getName());
		userToUpdate.getPreferences().setNotify(user.getPreferences().isNotify());

		return repository.save(userToUpdate);
	}

}
