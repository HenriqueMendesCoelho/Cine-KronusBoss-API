package com.kronusboss.cine.movie.usecase;

import java.util.UUID;

import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieNoteException;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;

public interface CreateMovieNoteUseCase {

	MovieNote create(UUID idMovie, Integer note, String emailUserLoged)
			throws MovieNotFoundException, DuplicatedMovieNoteException;
}
