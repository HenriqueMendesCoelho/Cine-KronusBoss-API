package com.kronusboss.cine.adapter.user.repository.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kronusboss.cine.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	
	@Transactional(readOnly = true)
	@EntityGraph(attributePaths = {"roles", "statistics"})
	User findByEmail(String email);
	
}
