package com.kronusboss.cine.discord.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.discord.adapter.repository.rest.DiscordRepository;
import com.kronusboss.cine.discord.domain.DiscordWebhookInfo;
import com.kronusboss.cine.discord.usecase.UpdateMessageWebhookUseCase;
import com.kronusboss.cine.movie.adapter.repository.jpa.MovieRepository;
import com.kronusboss.cine.movie.domain.Movie;

@Component
public class UpdateMessageWebhookUseCaseImpl implements UpdateMessageWebhookUseCase {

	@Autowired
	private DiscordRepository repository;

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public void updateMovieMessage(UUID movieId) {
		Movie movie = movieRepository.findById(movieId).orElse(null);

		if (movie == null) {
			return;
		}

		if (movie.getMovieDiscord() == null || movie.getMovieDiscord().getMessageId() == null
				|| movie.getMovieDiscord().getMessageId().isBlank()
				|| movie.getMovieDiscord().getMessageId().isEmpty()) {
			return;
		}

		DiscordWebhookInfo info = repository.update(movie);

		if (info.getMessageId() == null) {
			return;
		}

		movieRepository.saveAndFlush(movie);
	}

}
