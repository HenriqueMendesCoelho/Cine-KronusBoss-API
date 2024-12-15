package com.moviemux.user.usecase.impl;

import com.moviemux.user.domain.Invite;
import com.moviemux.user.usecase.DeleteInviteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moviemux.user.adapter.repository.InviteRepository;

@Component
public class DeleteInviteUseCaseImpl implements DeleteInviteUseCase {

	@Autowired
	InviteRepository repository;

	@Override
	public void delete(String code) {
		Invite invite = repository.findByCode(code);

		if (invite == null) {
			return;
		}

		repository.delete(invite);
	}

}
