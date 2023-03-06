package com.kronusboss.cine.adapter.user.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.kronusboss.cine.user.domain.Invite;

public interface InviteRepository extends JpaRepository<Invite, Long> {

	@Transactional(readOnly = true)
	Invite findByCode(String code);
}
