package com.kronusboss.cine.movie.adapter.repository.rest;

import java.util.UUID;

import com.kronusboss.cine.movie.domain.MovieNote;

public interface MovieSocketRespository {

	void emitAllMoviesEvent(String event);

	void emitEventMovie(UUID movieId, String event, MovieNote note);

}
