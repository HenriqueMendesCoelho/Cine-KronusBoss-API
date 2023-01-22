package com.kronusboss.cine.usecase.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kronusboss.cine.adapter.repository.jpa.user.InviteRepository;
import com.kronusboss.cine.adapter.repository.jpa.user.UserRepository;
import com.kronusboss.cine.domain.user.Invite;
import com.kronusboss.cine.domain.user.Preferences;
import com.kronusboss.cine.domain.user.Roles;
import com.kronusboss.cine.domain.user.Statistics;
import com.kronusboss.cine.domain.user.User;
import com.kronusboss.cine.usecase.user.CreateUserUseCase;
import com.kronusboss.cine.usecase.user.exception.DuplicatedUserException;
import com.kronusboss.cine.usecase.user.exception.InviteNotValidException;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {

	@Autowired
	private UserRepository repository;

	@Autowired
	private InviteRepository inviteRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User save(User user, String inviteCode) throws DuplicatedUserException, InviteNotValidException {

		if (repository.count() > 0) {
			Invite invite = inviteRepository.findByCode(inviteCode);

			if (invite == null) {
				throw new InviteNotValidException();
			}

			inviteRepository.delete(invite);
		} else {
			user.addRole(Roles.ADM);
		}

		if (repository.findByEmail(user.getEmail()) != null) {
			throw new DuplicatedUserException();
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setStatistics(Statistics.builder().user(user).ratingsGiven(0).registeredMovies(0).build());
		user.setPreferences(Preferences.builder().user(user).notify(true).build());

		return repository.saveAndFlush(user);
	}

}
