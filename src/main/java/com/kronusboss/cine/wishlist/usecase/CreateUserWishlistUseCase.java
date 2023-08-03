package com.kronusboss.cine.wishlist.usecase;

import java.util.UUID;

import com.kronusboss.cine.wishlist.domain.Wishlist;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistDuplicatedException;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistUserReachedLimitException;

public interface CreateUserWishlistUseCase {

	Wishlist create(String name, UUID userId) throws WishlistDuplicatedException, WishlistUserReachedLimitException;
}
