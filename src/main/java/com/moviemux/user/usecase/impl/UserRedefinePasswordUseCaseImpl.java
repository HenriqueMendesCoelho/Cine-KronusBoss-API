package com.moviemux.user.usecase.impl;

import com.moviemux.kronusintegrationtool.adapter.repository.rest.KronusIntegrationToolRepository;
import com.moviemux.kronusintegrationtool.domain.SendMailTemplate;
import com.moviemux.user.adapter.repository.UserRepository;
import com.moviemux.user.domain.User;
import com.moviemux.user.usecase.UserRedefinePasswordUseCase;
import com.moviemux.user.usecase.exception.UserNotAuthorizedException;
import com.moviemux.user.usecase.exception.UserRedefinePasswordKeyInvalid;
import com.moviemux.user.usecase.exception.UserRedefinePasswordKeyNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Random;

@Component
public class UserRedefinePasswordUseCaseImpl implements UserRedefinePasswordUseCase {

	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private KronusIntegrationToolRepository kronusIntegrationToolRepository;

	@Async
	@Override
	public void createRedefinePassword(String email) {
		User user = repository.findByEmail(email);
		if (user == null) {
			return;
		}

		String key = generateRedefineKeyPassword();

		kronusIntegrationToolRepository.sendMailTemplate(
				SendMailTemplate.forgotPasswordMail(user.getEmail(), user.getName(), key));

		user.setRedefinePasswordKey(key);
		user.setRedefinePasswordKeyCreatedAt(OffsetDateTime.now());

		repository.saveAndFlush(user);
	}

	@Override
	public User redefine(String redefinePasswordKey, String email, String password)
			throws UserRedefinePasswordKeyNotFound, UserRedefinePasswordKeyInvalid, UserNotAuthorizedException {
		User user = repository.findByRedefinePasswordKey(redefinePasswordKey);

		if (user == null) {
			throw new UserRedefinePasswordKeyNotFound();
		}

		if (!email.equalsIgnoreCase(user.getEmail())) {
			throw new UserNotAuthorizedException("Email provided not known with target user");
		}

		Duration duration = Duration.between(user.getRedefinePasswordKeyCreatedAt(), OffsetDateTime.now());
		long hours = duration.toHours();
		if (hours >= 1) {
			cleanRedefinePassowordKeyAndSave(user);
			throw new UserRedefinePasswordKeyInvalid();
		}

		user.setPassword(passwordEncoder.encode(password));
		return cleanRedefinePassowordKeyAndSave(user);
	}

	private User cleanRedefinePassowordKeyAndSave(User user) {
		user.setRedefinePasswordKey(null);
		user.setRedefinePasswordKeyCreatedAt(null);
		return repository.saveAndFlush(user);
	}

	private String generateRedefineKeyPassword() {
		final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final int TEXT_LENGTH = 200;

		Random random = new Random();
		StringBuilder sb = new StringBuilder(TEXT_LENGTH);

		for (int i = 0; i < TEXT_LENGTH; i++) {
			int index = random.nextInt(CHARACTERS.length());
			char randomChar = CHARACTERS.charAt(index);
			sb.append(randomChar);
		}

		return sb.toString();
	}

}
