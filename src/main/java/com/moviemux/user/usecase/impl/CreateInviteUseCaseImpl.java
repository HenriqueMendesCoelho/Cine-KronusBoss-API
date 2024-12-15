package com.moviemux.user.usecase.impl;

import com.moviemux.user.domain.Invite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviemux.user.adapter.repository.InviteRepository;
import com.moviemux.user.usecase.CreateInviteUseCase;

@Service
public class CreateInviteUseCaseImpl implements CreateInviteUseCase {

	@Autowired
	InviteRepository repository;

	@Override
	public Invite create() {

		Invite invite;

		do {
			invite = new Invite();
		} while (repository.findByCode(invite.getCode()) != null);

		return repository.saveAndFlush(invite);
	}

}
