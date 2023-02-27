package com.kronusboss.cine.user.usecase.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.KronusIntegrationToolRepository;
import com.kronusboss.cine.adapter.user.repository.jpa.InviteRepository;
import com.kronusboss.cine.adapter.user.repository.jpa.UserRepository;
import com.kronusboss.cine.kronusintegrationtool.domain.SendMailTemplate;
import com.kronusboss.cine.user.domain.Invite;
import com.kronusboss.cine.user.domain.Preferences;
import com.kronusboss.cine.user.domain.Role;
import com.kronusboss.cine.user.domain.Statistics;
import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.user.usecase.CreateUserUseCase;
import com.kronusboss.cine.user.usecase.exception.DuplicatedUserException;
import com.kronusboss.cine.user.usecase.exception.InviteNotValidException;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CreateUserUseCaseImpl implements CreateUserUseCase {

	@Autowired
	private UserRepository repository;

	@Autowired
	private InviteRepository inviteRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	KronusIntegrationToolRepository kronusIntegrationToolRepository;

	@Override
	public User save(User user, String inviteCode) throws DuplicatedUserException, InviteNotValidException {

		if (repository.count() > 0) {
			Invite invite = inviteRepository.findByCode(inviteCode);

			if (invite == null) {
				throw new InviteNotValidException();
			}

			inviteRepository.delete(invite);
		} else {
			user.addRole(Role.ADM);
		}

		if (repository.findByEmail(user.getEmail()) != null) {
			throw new DuplicatedUserException();
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setStatistics(Statistics.builder().user(user).ratingsGiven(0).registeredMovies(0).build());
		user.setPreferences(Preferences.builder().user(user).notify(true).build());

		User userCreated = repository.saveAndFlush(user);

		sendWelcomeMail(userCreated);

		return userCreated;
	}

	private void sendWelcomeMail(User user) {
		try {
			kronusIntegrationToolRepository
					.sendMailTemplate(SendMailTemplate.welcomeMail(user.getEmail(), user.getName()));
		} catch (Exception e) {
			log.error(String.format("Error to send welcome mail to %s", user.getName()));
		}
	}
}
