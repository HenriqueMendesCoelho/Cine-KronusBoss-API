package com.kronusboss.cine.usecase.user.exception;

public class UserNotFoundException extends ReflectiveOperationException {
	
	private static final long serialVersionUID = 2800307934478206153L;
	
	public UserNotFoundException() {
		super("user not found");
	}

}
