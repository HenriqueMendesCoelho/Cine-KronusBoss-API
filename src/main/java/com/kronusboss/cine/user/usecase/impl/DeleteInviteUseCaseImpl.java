package com.kronusboss.cine.user.usecase.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.user.repository.jpa.InviteRepository;
import com.kronusboss.cine.user.domain.Invite;
import com.kronusboss.cine.user.usecase.DeleteInviteUseCase;

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
