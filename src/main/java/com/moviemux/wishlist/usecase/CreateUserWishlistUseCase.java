package com.moviemux.wishlist.usecase;

import java.util.UUID;

import com.moviemux.wishlist.domain.Wishlist;
import com.moviemux.wishlist.usecase.exception.WishlistDuplicatedException;
import com.moviemux.wishlist.usecase.exception.WishlistUserReachedLimitException;

public interface CreateUserWishlistUseCase {

	Wishlist create(String name, UUID userId) throws WishlistDuplicatedException, WishlistUserReachedLimitException;
}
