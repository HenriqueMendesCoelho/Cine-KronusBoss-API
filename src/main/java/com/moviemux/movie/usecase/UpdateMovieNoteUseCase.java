package com.moviemux.movie.usecase;

import java.util.UUID;

import com.moviemux.movie.domain.MovieNote;
import com.moviemux.movie.usecase.exception.MovieNoteNotFoundException;

public interface UpdateMovieNoteUseCase {

	MovieNote update(UUID userId, UUID movieId, Integer note) throws MovieNoteNotFoundException;

}
