package com.moviemux.movie.usecase;

import java.util.UUID;

import com.moviemux.movie.domain.MovieNote;
import com.moviemux.movie.usecase.exception.DuplicatedMovieNoteException;
import com.moviemux.movie.usecase.exception.MovieNotFoundException;

public interface CreateMovieNoteUseCase {

	MovieNote create(UUID idMovie, Integer note, String emailUserLoged)
			throws MovieNotFoundException, DuplicatedMovieNoteException;
}
