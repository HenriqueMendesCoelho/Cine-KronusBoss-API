package com.kronusboss.cine.movie.adapter.repository.rest;

import com.kronusboss.cine.movie.domain.MovieNote;

import java.util.UUID;

public interface MovieSocketRespository {

	void emitAllMoviesEvent(String event);

	void emitEventMovie(UUID movieId, String event, MovieNote note, String emmitedByUserId);

}
