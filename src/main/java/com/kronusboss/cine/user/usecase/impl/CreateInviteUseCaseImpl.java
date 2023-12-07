package com.kronusboss.cine.user.usecase.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kronusboss.cine.user.adapter.repository.InviteRepository;
import com.kronusboss.cine.user.domain.Invite;
import com.kronusboss.cine.user.usecase.CreateInviteUseCase;

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
