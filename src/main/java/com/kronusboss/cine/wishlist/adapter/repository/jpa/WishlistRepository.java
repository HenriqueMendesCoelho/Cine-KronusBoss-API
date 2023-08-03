package com.kronusboss.cine.wishlist.adapter.repository.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.kronusboss.cine.wishlist.domain.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, UUID> {

	@Transactional(readOnly = true)
	List<Wishlist> findByUserIdOrderByCreatedAtDesc(UUID userId);

}
