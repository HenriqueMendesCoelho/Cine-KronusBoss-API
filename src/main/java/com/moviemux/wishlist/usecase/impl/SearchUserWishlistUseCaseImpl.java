package com.moviemux.wishlist.usecase.impl;

import java.util.List;
import java.util.UUID;

import com.moviemux.wishlist.usecase.SearchUserWishlistUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moviemux.wishlist.adapter.repository.WishlistRepository;
import com.moviemux.wishlist.domain.Wishlist;
import com.moviemux.wishlist.usecase.exception.WishlistNotFoundException;

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
