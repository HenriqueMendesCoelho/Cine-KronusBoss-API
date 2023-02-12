package com.kronusboss.cine.movie.usecase.exception;

public class MovieNotFoundException extends ReflectiveOperationException {

	private static final long serialVersionUID = 4087604245442641655L;
	
	public MovieNotFoundException() {
		super("Movie not found");
	}
	
}
