package com.kronusboss.cine.user.usecase.exception;

public class UserRedefinePasswordKeyInvalid extends ReflectiveOperationException {

	private static final long serialVersionUID = 7130451935243291369L;

	public UserRedefinePasswordKeyInvalid() {
		super("user Redefine Password Key invalid");
	}
}
