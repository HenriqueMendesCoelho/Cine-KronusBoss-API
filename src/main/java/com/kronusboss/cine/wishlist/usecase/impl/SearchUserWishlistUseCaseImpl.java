package com.kronusboss.cine.wishlist.usecase.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.wishlist.adapter.repository.jpa.WishlistRepository;
import com.kronusboss.cine.wishlist.domain.Wishlist;
import com.kronusboss.cine.wishlist.usecase.SearchUserWishlistUseCase;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistNotFoundException;

@Component
public class SearchUserWishlistUseCaseImpl implements SearchUserWishlistUseCase {

	@Autowired
	private WishlistRepository repository;

	@Override
	public List<Wishlist> list(UUID userId) {
		return repository.findByUserIdOrderByCreatedAtDesc(userId);
	}

	@Override
	public Wishlist findById(UUID wishlistId, UUID userId) throws WishlistNotFoundException {
		Wishlist wishlist = repository.findById(wishlistId).orElse(null);

		if (wishlist == null || (!wishlist.getUser().getId().equals(userId) && !wishlist.isShareable())) {
			throw new WishlistNotFoundException();
		}

		return wishlist;
	}

}
