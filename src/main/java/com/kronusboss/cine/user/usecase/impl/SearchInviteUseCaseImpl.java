package com.kronusboss.cine.user.usecase.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kronusboss.cine.user.adapter.repository.InviteRepository;
import com.kronusboss.cine.user.domain.Invite;
import com.kronusboss.cine.user.usecase.SearchInviteUseCase;

@Service
public class SearchInviteUseCaseImpl implements SearchInviteUseCase {

	@Autowired
	private InviteRepository repository;

	@Override
	public List<Invite> list() {
		return repository.findAll();
	}

}
