package com.kronusboss.cine.application.spring.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.user.repository.jpa.UserRepository;
import com.kronusboss.cine.user.domain.User;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		String email = (String) event.getAuthentication().getPrincipal();
		
		if(email.isBlank()) {
			return;
		}
		
		failedLoginAttempts(email);
	}
	
	private void failedLoginAttempts(String email) {
		User user = repository.findByEmail(email);
		
		if(user == null) {
			return;
		}
		
		int ConsecutiveFailedLoginAttempts = user.getStatistics().getConsecutiveFailedLoginAttempts();
		user.getStatistics().setConsecutiveFailedLoginAttempts(ConsecutiveFailedLoginAttempts + 1);
		
		repository.saveAndFlush(user);
	}
}
