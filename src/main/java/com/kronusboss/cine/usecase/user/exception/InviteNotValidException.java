package com.kronusboss.cine.usecase.user.exception;

public class InviteNotValidException extends ReflectiveOperationException {

	private static final long serialVersionUID = -4047294197057224637L;

	public InviteNotValidException() {
		super("invite is not valid");
	}

}
