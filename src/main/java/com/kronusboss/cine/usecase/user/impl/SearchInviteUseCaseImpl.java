package com.kronusboss.cine.usecase.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kronusboss.cine.adapter.repository.jpa.user.InviteRepository;
import com.kronusboss.cine.domain.user.Invite;
import com.kronusboss.cine.usecase.user.SearchInviteUseCase;

@Service
public class SearchInviteUseCaseImpl implements SearchInviteUseCase {
	
	@Autowired
	private InviteRepository repository;

	@Override
	public List<Invite> list() {
		return repository.findAll();
	}

}
