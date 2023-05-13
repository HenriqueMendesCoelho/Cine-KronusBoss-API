package com.kronusboss.cine.movie.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.discord.usecase.UpdateMessageWebhookUseCase;
import com.kronusboss.cine.movie.adapter.repository.jpa.MovieNoteRepository;
import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.domain.MovieNoteKey;
import com.kronusboss.cine.movie.usecase.DeleteMovieNoteUseCase;

@Component
public class DeleteMovieNoteUseCaseImpl implements DeleteMovieNoteUseCase {

	@Autowired
	private MovieNoteRepository repository;

	@Autowired
	private UpdateMessageWebhookUseCase updateMessageWebhookUseCase;

	@Override
	public void delete(UUID userId, UUID movieId) {
		MovieNote movieNoteToDelete = repository.findById(new MovieNoteKey(userId, movieId)).orElse(null);

		if (movieNoteToDelete == null) {
			return;
		}

		repository.delete(movieNoteToDelete);
		updateMessageWebhookUseCase.updateMovieMessage(movieId);
	}

}
