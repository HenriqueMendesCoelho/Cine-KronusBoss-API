package com.kronusboss.cine.wishlist.usecase;

import java.util.List;
import java.util.UUID;

import com.kronusboss.cine.wishlist.domain.Wishlist;
import com.kronusboss.cine.wishlist.usecase.exception.WishlistNotFoundException;

public interface SearchUserWishlistUseCase {

	List<Wishlist> list(UUID userId);

	Wishlist findById(UUID wishlistId, UUID userId) throws WishlistNotFoundException;
}
