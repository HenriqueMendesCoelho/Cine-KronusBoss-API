package com.kronusboss.cine.adapter.repository.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.kronusboss.cine.domain.user.Invite;

public interface InviteRepository extends JpaRepository<Invite, Long> {
	
	@Transactional(readOnly = true)
	Invite findByCode(String code);
}
