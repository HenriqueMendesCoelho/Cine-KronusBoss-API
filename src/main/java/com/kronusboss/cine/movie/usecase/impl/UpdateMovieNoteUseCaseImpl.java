package com.kronusboss.cine.movie.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.discord.usecase.UpdateMessageWebhookUseCase;
import com.kronusboss.cine.movie.adapter.repository.MovieNoteRepository;
import com.kronusboss.cine.movie.adapter.repository.rest.MovieSocketRespository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.domain.MovieNoteKey;
import com.kronusboss.cine.movie.usecase.UpdateMovieNoteUseCase;
import com.kronusboss.cine.movie.usecase.exception.MovieNoteNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UpdateMovieNoteUseCaseImpl implements UpdateMovieNoteUseCase {

	@Autowired
	private MovieNoteRepository repository;

	@Autowired
	private UpdateMessageWebhookUseCase updateMessageWebhookUseCase;

	@Autowired
	private MovieSocketRespository movieSocketRespository;

	@Override
	public MovieNote update(UUID userId, UUID movieId, Integer note) throws MovieNoteNotFoundException {
		MovieNote movieNoteToUpdate = repository.findById(new MovieNoteKey(userId, movieId)).orElse(null);

		if (movieNoteToUpdate == null) {
			throw new MovieNoteNotFoundException();
		}

		movieNoteToUpdate.setNote(note);

		MovieNote noteUpdated = repository.saveAndFlush(movieNoteToUpdate);
		updateMessageWebhookUseCase.updateMovieMessage(movieNoteToUpdate.getMovie().getId());
		sendEventSocket(noteUpdated);

		return noteUpdated;
	}

	private void sendEventSocket(MovieNote note) {
		try {
			MovieNote eventContent = note.clone();
			Movie movie = note.getMovie();

			if (!movie.isShowNotes()) {
				eventContent.setNote(null);
			}

			movieSocketRespository.emitEventMovie(movie.getId(), "update-note", eventContent);
		} catch (CloneNotSupportedException e) {
			log.error("Error to emit event on note update: ", e);
		}

	}

}
