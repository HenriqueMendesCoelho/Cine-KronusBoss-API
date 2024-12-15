package com.moviemux.wishlist.usecase;

import java.util.UUID;

import com.moviemux.user.usecase.exception.UserNotAuthorizedException;
import com.moviemux.wishlist.domain.Wishlist;
import com.moviemux.wishlist.usecase.exception.WishlistMovieAlreadyExistsException;
import com.moviemux.wishlist.usecase.exception.WishlistNotFoundException;

public interface UpdateUserWishlistUseCase {

	Wishlist update(Wishlist userWishlist, UUID userId)
			throws WishlistNotFoundException, WishlistMovieAlreadyExistsException, UserNotAuthorizedException;

	Wishlist addMovieToWishlist(UUID wishlistId, UUID userId, Long tmdbId)
			throws WishlistNotFoundException, WishlistMovieAlreadyExistsException;
}
