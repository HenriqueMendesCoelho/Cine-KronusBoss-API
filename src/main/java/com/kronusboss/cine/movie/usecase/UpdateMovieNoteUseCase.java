package com.kronusboss.cine.movie.usecase;

import java.util.UUID;

import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.usecase.exception.MovieNoteNotFoundException;

public interface UpdateMovieNoteUseCase {

	MovieNote update(UUID userId, UUID movieId, Integer note) throws MovieNoteNotFoundException;

}
