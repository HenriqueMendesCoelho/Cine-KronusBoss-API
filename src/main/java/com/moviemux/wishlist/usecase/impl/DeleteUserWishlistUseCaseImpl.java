package com.moviemux.wishlist.usecase.impl;

import java.util.UUID;

import com.moviemux.wishlist.usecase.DeleteUserWishlistUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moviemux.wishlist.adapter.repository.MoviesWishlistsRepository;
import com.moviemux.wishlist.adapter.repository.WishlistRepository;
import com.moviemux.wishlist.domain.Wishlist;

@Component
public class DeleteUserWishlistUseCaseImpl implements DeleteUserWishlistUseCase {

	@Autowired
	private WishlistRepository repository;

	@Autowired
	private MoviesWishlistsRepository moviesWishlistsRepository;

	@Override
	public void delete(UUID wishlistId, UUID userId) {
		Wishlist wishlist = repository.findById(wishlistId).orElse(null);
		if (wishlist == null || !wishlist.getUser().getId().equals(userId)) {
			return;
		}
		moviesWishlistsRepository.deleteAll(wishlist.getMoviesWishlists());
		repository.delete(wishlist);

	}

}
