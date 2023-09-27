package com.kronusboss.cine.wishlist.usecase.exception;

public class WishlistDuplicatedException extends ReflectiveOperationException {

	private static final long serialVersionUID = 2800307934478206153L;

	public WishlistDuplicatedException(String name) {
		super("Wishlist already created with the name %s".formatted(name));
	}

}
