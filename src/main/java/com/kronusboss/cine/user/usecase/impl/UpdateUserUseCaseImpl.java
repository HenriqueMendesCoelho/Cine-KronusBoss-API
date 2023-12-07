package com.kronusboss.cine.user.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kronusboss.cine.user.adapter.repository.UserRepository;
import com.kronusboss.cine.user.domain.Role;
import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.user.usecase.UpdateUserUseCase;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.user.usecase.exception.UserNotFoundException;

@Service
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

	@Autowired
	UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User update(User user, UUID userId, String emailUserLoged)
			throws UserNotFoundException, UserNotAuthorizedException {
		User userLoged = repository.findByEmail(emailUserLoged);
		User userToUpdate = repository.getReferenceById(userId);

		if (userLoged.getId() != userId && !userLoged.getRoles().contains(Role.ADM)) {
			throw new UserNotAuthorizedException();
		}

		if (userToUpdate == null) {
			throw new UserNotFoundException();
		}

		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setName(user.getName());
		userToUpdate.getPreferences().setNotify(user.getPreferences().isNotify());

		return repository.saveAndFlush(userToUpdate);
	}

	@Override
	public User updateUserProfile(UUID userId, String name, String email, boolean notify) throws UserNotFoundException {
		User userToUpdate = repository.findById(userId).orElse(null);

		if (userToUpdate == null) {
			throw new UserNotFoundException();
		}

		userToUpdate.setName(name);
		userToUpdate.setEmail(email);
		userToUpdate.getPreferences().setNotify(notify);

		return repository.saveAndFlush(userToUpdate);
	}

	@Override
	public User updateUserPassoword(UUID userId, String password, String newPassword)
			throws UserNotFoundException, UserNotAuthorizedException {
		User user = repository.findById(userId).orElse(null);

		if (user == null) {
			throw new UserNotFoundException();
		}

		boolean passwordIsValid = passwordEncoder.matches(password, user.getPassword());

		if (!passwordIsValid) {
			throw new UserNotAuthorizedException();
		}

		user.setPassword(passwordEncoder.encode(newPassword));

		return repository.saveAndFlush(user);
	}

	@Override
	public User promoteUserToAdmin(UUID userId) throws UserNotFoundException {
		User user = repository.findById(userId).orElse(null);

		if (user == null) {
			throw new UserNotFoundException();
		}

		boolean isAlreadyAdm = user.getRoles().stream().anyMatch(r -> r.getDescription() == "ROLE_ADM");

		if (isAlreadyAdm) {
			return user;
		}

		user.addRole(Role.ADM);

		return repository.saveAndFlush(user);
	}

	@Override
	public User demoteUserToAdmin(UUID userId) throws UserNotFoundException {
		User user = repository.findById(userId).orElse(null);

		if (user == null) {
			throw new UserNotFoundException();
		}

		boolean isAlreadyAdm = user.getRoles().stream().anyMatch(r -> r.getDescription() == "ROLE_ADM");

		if (!isAlreadyAdm) {
			return user;
		}

		user.removeRole(Role.ADM);

		return repository.saveAndFlush(user);
	}

	@Override
	public User blockUser(UUID userId) throws UserNotFoundException {
		User user = repository.findById(userId).orElse(null);

		if (user == null) {
			throw new UserNotFoundException();
		}

		user.getStatistics().setConsecutiveFailedLoginAttempts(10);

		return repository.saveAndFlush(user);
	}

}
