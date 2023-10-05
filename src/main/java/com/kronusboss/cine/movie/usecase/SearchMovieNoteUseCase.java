package com.kronusboss.cine.movie.usecase;

import java.util.List;
import java.util.UUID;

import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;

public interface SearchMovieNoteUseCase {

	List<MovieNote> list(UUID movieId, UUID userId) throws MovieNotFoundException;
}
