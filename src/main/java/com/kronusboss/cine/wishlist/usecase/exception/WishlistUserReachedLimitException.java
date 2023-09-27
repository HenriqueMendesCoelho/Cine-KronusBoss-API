package com.kronusboss.cine.wishlist.usecase.exception;

public class WishlistUserReachedLimitException extends Exception {

	private static final long serialVersionUID = 7069877641738873394L;

	public WishlistUserReachedLimitException() {
		super("User has reached wishlits limit");
	}

}
