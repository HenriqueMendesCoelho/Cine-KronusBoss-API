package com.moviemux.user.usecase.impl;

import java.util.List;

import com.moviemux.user.domain.Invite;
import com.moviemux.user.usecase.SearchInviteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviemux.user.adapter.repository.InviteRepository;

@Service
public class SearchInviteUseCaseImpl implements SearchInviteUseCase {

	@Autowired
	private InviteRepository repository;

	@Override
	public List<Invite> list() {
		return repository.findAll();
	}

}
