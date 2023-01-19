package com.kronusboss.cine.usecase.user.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.repository.jpa.user.UserRepository;
import com.kronusboss.cine.domain.user.User;
import com.kronusboss.cine.usecase.user.SearchUserUseCase;
import com.kronusboss.cine.usecase.user.exception.UserNotFoundException;

@Component
public class SearchUserUseCaseImpl implements SearchUserUseCase {
	
	@Autowired
	UserRepository repository;
	
	@Override
	public User get(UUID id) throws UserNotFoundException {
		User user = repository.getReferenceById(id);
		
		if(user == null) {
			throw new UserNotFoundException();
		}
		
		return user;
	}

}
