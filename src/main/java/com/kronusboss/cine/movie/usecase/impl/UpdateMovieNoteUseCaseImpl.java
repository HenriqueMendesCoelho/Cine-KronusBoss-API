package com.kronusboss.cine.movie.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.discord.usecase.UpdateMessageWebhookUseCase;
import com.kronusboss.cine.movie.adapter.repository.jpa.MovieNoteRepository;
import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.domain.MovieNoteKey;
import com.kronusboss.cine.movie.usecase.UpdateMovieNoteUseCase;
import com.kronusboss.cine.movie.usecase.exception.MovieNoteNotFoundException;

@Component
public class UpdateMovieNoteUseCaseImpl implements UpdateMovieNoteUseCase {

	@Autowired
	private MovieNoteRepository repository;

	@Autowired
	private UpdateMessageWebhookUseCase updateMessageWebhookUseCase;

	@Override
	public MovieNote update(UUID userId, UUID movieId, Integer note) throws MovieNoteNotFoundException {
		MovieNote movieNoteToUpdate = repository.findById(new MovieNoteKey(userId, movieId)).orElse(null);

		if (movieNoteToUpdate == null) {
			throw new MovieNoteNotFoundException();
		}

		movieNoteToUpdate.setNote(note);

		MovieNote noteUpdated = repository.saveAndFlush(movieNoteToUpdate);
		updateMessageWebhookUseCase.updateMovieMessage(movieNoteToUpdate.getMovie().getId());

		return noteUpdated;
	}

}
