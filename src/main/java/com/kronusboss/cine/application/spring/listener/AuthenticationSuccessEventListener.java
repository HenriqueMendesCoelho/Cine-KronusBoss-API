package com.kronusboss.cine.application.spring.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.user.repository.jpa.UserRepository;
import com.kronusboss.cine.user.domain.User;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private UserRepository repository;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		String email = (String) event.getAuthentication().getName();

		if (email.isBlank()) {
			return;
		}

		resetFailedLoginAttempts(email);
	}

	private void resetFailedLoginAttempts(String email) {
		User user = repository.findByEmail(email);

		if (user == null) {
			return;
		}

		user.getStatistics().setConsecutiveFailedLoginAttempts(0);

		repository.saveAndFlush(user);
	}

}
