package com.kronusboss.cine.usecase.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kronusboss.cine.adapter.repository.jpa.user.InviteRepository;
import com.kronusboss.cine.domain.user.Invite;
import com.kronusboss.cine.usecase.user.CreateInviteUseCase;

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
