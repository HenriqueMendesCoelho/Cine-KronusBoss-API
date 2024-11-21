package com.moviemux.movie.usecase;

import java.util.List;
import java.util.UUID;

import com.moviemux.movie.domain.MovieNote;
import com.moviemux.movie.usecase.exception.MovieNotFoundException;

public interface SearchMovieNoteUseCase {

	List<MovieNote> list(UUID movieId, UUID userId) throws MovieNotFoundException;
}
