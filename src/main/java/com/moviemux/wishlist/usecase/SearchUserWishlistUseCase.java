package com.moviemux.wishlist.usecase;

import java.util.List;
import java.util.UUID;

import com.moviemux.wishlist.domain.Wishlist;
import com.moviemux.wishlist.usecase.exception.WishlistNotFoundException;

public interface SearchUserWishlistUseCase {

	List<Wishlist> list(UUID userId);

	Wishlist findById(UUID wishlistId, UUID userId) throws WishlistNotFoundException;
}
