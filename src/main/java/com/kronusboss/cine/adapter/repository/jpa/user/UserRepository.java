package com.kronusboss.cine.adapter.repository.jpa.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kronusboss.cine.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	
	@Transactional(readOnly = true)
	User findByEmail(String email);
	
}
