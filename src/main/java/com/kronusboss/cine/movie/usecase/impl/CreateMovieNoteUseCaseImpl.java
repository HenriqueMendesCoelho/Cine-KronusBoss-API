package com.kronusboss.cine.movie.usecase.impl;

import com.kronusboss.cine.discord.usecase.UpdateMessageWebhookUseCase;
import com.kronusboss.cine.movie.adapter.repository.MovieNoteRepository;
import com.kronusboss.cine.movie.adapter.repository.MovieRepository;
import com.kronusboss.cine.movie.adapter.repository.rest.MovieSocketRespository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.domain.MovieNoteKey;
import com.kronusboss.cine.movie.usecase.CreateMovieNoteUseCase;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieNoteException;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;
import com.kronusboss.cine.user.adapter.repository.UserRepository;
import com.kronusboss.cine.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class CreateMovieNoteUseCaseImpl implements CreateMovieNoteUseCase {

	@Autowired
	private MovieNoteRepository repository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UpdateMessageWebhookUseCase updateMessageWebhookUseCase;

	@Autowired
	private MovieSocketRespository movieSocketRespository;

	@Override
	@CacheEvict(value = "statistics", allEntries = true)
	public MovieNote create(UUID movieId, Integer note, String emailUserLoged)
			throws MovieNotFoundException, DuplicatedMovieNoteException {
		User user = userRepository.findByEmail(emailUserLoged);
		Movie movie = movieRepository.findById(movieId).orElse(null);

		if (movie == null) {
			throw new MovieNotFoundException();
		}

		MovieNote movieNoteExists = repository.findById(new MovieNoteKey(user.getId(), movie.getId())).orElse(null);

		if (movieNoteExists != null) {
			throw new DuplicatedMovieNoteException();
		}

		MovieNote movieNote = MovieNote.builder().movie(movie).note(note).user(user).build();
		userRepository.saveAndFlush(user);
		MovieNote noteCreated = repository.saveAndFlush(movieNote);
		updateMessageWebhookUseCase.updateMovieMessage(movie.getId());
		sendEventSocket(noteCreated);

		return noteCreated;
	}

	private void sendEventSocket(MovieNote note) {
		try {
			MovieNote eventContent = note.clone();
			Movie movie = note.getMovie();

			if (!movie.isShowNotes()) {
				eventContent.setNote(null);
			}

			movieSocketRespository.emitEventMovie(movie.getId(), "create-note", eventContent);
		} catch (Exception e) {
			log.error("Error to emit event on note create (CreateMovieNoteUseCaseImpl): ", e);
		}

	}

}
