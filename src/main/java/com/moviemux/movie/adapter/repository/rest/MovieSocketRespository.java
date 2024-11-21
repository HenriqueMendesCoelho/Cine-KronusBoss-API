package com.moviemux.movie.adapter.repository.rest;

import com.moviemux.movie.domain.MovieNote;

import java.util.UUID;

public interface MovieSocketRespository {

	void emitAllMoviesEvent(String event);

	void emitEventMovie(UUID movieId, String event, MovieNote note, String emmitedByUserId);

}
