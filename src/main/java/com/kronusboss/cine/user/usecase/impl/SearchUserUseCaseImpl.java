package com.kronusboss.cine.user.usecase.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kronusboss.cine.adapter.user.repository.jpa.UserRepository;
import com.kronusboss.cine.user.domain.Role;
import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.user.usecase.SearchUserUseCase;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.user.usecase.exception.UserNotFoundException;

@Service
public class SearchUserUseCaseImpl implements SearchUserUseCase {
	
	@Autowired
	UserRepository repository;
	
	@Override
	public User getUserByEmail(String emailUserToFind, String emailUserLoged) throws UserNotFoundException, UserNotAuthorizedException {
		User userLoged = repository.findByEmail(emailUserLoged);
		User user = repository.findByEmail(emailUserToFind);
		
		if(!emailUserToFind.equals(emailUserLoged) && !userLoged.getRoles().contains(Role.ADM)) {
			throw new UserNotAuthorizedException();
		}
		
		if(user == null) {
			throw new UserNotFoundException();
		}
		
		return user;
	}

	@Override
	public List<User> getAllUsers() throws UserNotFoundException {
		List<User> users = repository.findAll();
		
		if(CollectionUtils.isEmpty(users)) {
			throw new UserNotFoundException();
		}
		
		return users;
	}

}
