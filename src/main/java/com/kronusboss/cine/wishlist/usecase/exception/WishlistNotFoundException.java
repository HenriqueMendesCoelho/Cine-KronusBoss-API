package com.kronusboss.cine.wishlist.usecase.exception;

public class WishlistNotFoundException extends ReflectiveOperationException {

	private static final long serialVersionUID = 2800307934478206153L;

	public WishlistNotFoundException() {
		super("wishlist not found");
	}

}
