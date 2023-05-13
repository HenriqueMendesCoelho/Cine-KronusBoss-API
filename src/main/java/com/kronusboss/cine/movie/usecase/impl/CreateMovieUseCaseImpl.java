package com.kronusboss.cine.movie.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.discord.usecase.SendMessageWebhookUseCase;
import com.kronusboss.cine.movie.adapter.repository.jpa.MovieRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.CreateMovieUseCase;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieException;
import com.kronusboss.cine.user.adapter.repository.jpa.UserRepository;
import com.kronusboss.cine.user.domain.User;

@Component
public class CreateMovieUseCaseImpl implements CreateMovieUseCase {

	@Autowired
	private MovieRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SendMessageWebhookUseCase sendMessageWebhook;

	@Override
	public Movie save(Movie movie, UUID userId) throws DuplicatedMovieException {

		if (repository.findByTmdbId(movie.getTmdbId()) != null) {
			throw new DuplicatedMovieException("This tmdb id is already registered");
		}
		User user = userRepository.findById(userId).orElse(null);
		movie.setUser(user);

		userRepository.saveAndFlush(user);

		Movie movieCreated = repository.saveAndFlush(movie);

		sendMessageWebhook.sendMovieMessage(movieCreated.getId());

		return movieCreated;
	}

}
