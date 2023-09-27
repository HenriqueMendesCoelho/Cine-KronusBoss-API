package com.kronusboss.cine.wishlist.usecase;

import java.util.UUID;

import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import com.kronusboss.cine.wishlist.domain.Wishlist;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistMovieAlreadyExistsException;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistNotFoundException;

public interface UpdateUserWishlistUseCase {

	Wishlist update(Wishlist userWishlist, UUID userId)
			throws WishlistNotFoundException, WishlistMovieAlreadyExistsException, UserNotAuthorizedException;

	Wishlist addMovieToWishlist(UUID wishlistId, UUID userId, Long tmdbId)
			throws WishlistNotFoundException, WishlistMovieAlreadyExistsException;
}
