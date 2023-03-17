package com.kronusboss.cine.movie.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.movie.repository.jpa.MovieNoteRepository;
import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.domain.MovieNoteKey;
import com.kronusboss.cine.movie.usecase.UpdateMovieNoteUseCase;
import com.kronusboss.cine.movie.usecase.exception.MovieNoteNotFoundException;

@Component
public class UpdateMovieNoteUseCaseImpl implements UpdateMovieNoteUseCase {

	@Autowired
	private MovieNoteRepository repository;

	@Override
	public MovieNote update(UUID userId, UUID movieId, Integer note) throws MovieNoteNotFoundException {
		MovieNote movieNoteToUpdate = repository.findById(new MovieNoteKey(userId, movieId)).orElse(null);

		if (movieNoteToUpdate == null) {
			throw new MovieNoteNotFoundException();
		}

		movieNoteToUpdate.setNote(note);

		return repository.saveAndFlush(movieNoteToUpdate);
	}

}
