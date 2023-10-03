package com.kronusboss.cine.movie.adapter.repository.rest;

public interface MovieSocketRespository {

	void emitAllMoviesEvent(String event);

}
