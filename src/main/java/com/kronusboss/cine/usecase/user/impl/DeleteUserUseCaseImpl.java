package com.kronusboss.cine.usecase.user.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kronusboss.cine.adapter.repository.jpa.user.UserRepository;
import com.kronusboss.cine.domain.user.User;
import com.kronusboss.cine.usecase.user.DeleteUserUseCase;

@Service
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
	
	@Autowired
	UserRepository repository;

	@Override
	public void deleteUser(UUID id) {
		
		User userToDelete = repository.getReferenceById(id);
		
		if(userToDelete == null) {
			return;
		}
		
		repository.delete(userToDelete);
		
	}

}
