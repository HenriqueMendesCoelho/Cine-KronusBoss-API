package com.kronusboss.cine.wishlist.usecase;

import java.util.UUID;

public interface DeleteUserWishlistUseCase {

	void delete(UUID wishlistId, UUID userId);
}
