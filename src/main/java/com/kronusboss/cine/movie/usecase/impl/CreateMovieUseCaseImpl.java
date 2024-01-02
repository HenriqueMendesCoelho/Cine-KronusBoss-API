package com.kronusboss.cine.movie.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.discord.usecase.SendMessageWebhookUseCase;
import com.kronusboss.cine.movie.adapter.repository.MovieRepository;
import com.kronusboss.cine.movie.adapter.repository.rest.MovieSocketRespository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.CreateMovieUseCase;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieException;
import com.kronusboss.cine.user.adapter.repository.UserRepository;
import com.kronusboss.cine.user.domain.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CreateMovieUseCaseImpl implements CreateMovieUseCase {

	@Autowired
	private MovieRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SendMessageWebhookUseCase sendMessageWebhook;

	@Autowired
	private MovieSocketRespository socketRepository;

	@Override
	public Movie save(Movie movie, UUID userId) throws DuplicatedMovieException {

		if (repository.findByTmdbId(movie.getTmdbId()) != null) {
			throw new DuplicatedMovieException("This tmdb id is already registered");
		}

		User user = userRepository.findById(userId).orElse(null);
		movie.setUser(user);
		movie.setShowNotes(false);
		userRepository.saveAndFlush(user);
		Movie movieCreated = repository.saveAndFlush(movie);
		sendMessageDiscord(movieCreated.getId());
		emitEventSocket();

		return movieCreated;
	}

	private void sendMessageDiscord(UUID movieId) {
		try {
			sendMessageWebhook.sendMovieMessage(movieId);
		} catch (Exception e) {
			log.error("Error to send message on discord raised: ", e);
		}
	}

	private void emitEventSocket() {
		try {
			socketRepository.emitAllMoviesEvent(null);
		} catch (Exception e) {
			log.error("Error to emit event on movie creation raised: ", e);
		}
	}
}
