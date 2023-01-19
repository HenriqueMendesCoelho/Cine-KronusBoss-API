package com.kronusboss.cine.usecase.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.repository.jpa.user.UserRepository;
import com.kronusboss.cine.domain.user.User;
import com.kronusboss.cine.usecase.user.CreateUserUseCase;
import com.kronusboss.cine.usecase.user.exception.DuplicatedUserException;

@Component
public class CreateUserUseCaseImpl implements CreateUserUseCase {
	
	@Autowired
	private UserRepository repository;
	
	
	@Override
	public User save(User user) throws DuplicatedUserException {
		User verifyDuplicity = repository.findByEmail(user.getEmail());
		
		if(verifyDuplicity != null) {
			throw new DuplicatedUserException();
		}
		
		return repository.saveAndFlush(user);
	}

}
