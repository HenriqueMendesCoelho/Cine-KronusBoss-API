package com.kronusboss.cine.discord.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.discord.adapter.repository.rest.DiscordRepository;
import com.kronusboss.cine.discord.domain.DiscordWebhookInfo;
import com.kronusboss.cine.discord.usecase.SendMessageWebhookUseCase;
import com.kronusboss.cine.movie.adapter.repository.jpa.MovieRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.domain.MovieDiscord;

@Component
public class SendMessageWebhookUseCaseImpl implements SendMessageWebhookUseCase {

	@Autowired
	private DiscordRepository repository;

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public void sendMovieMessage(UUID movieId) {
		Movie movie = movieRepository.findById(movieId).orElse(null);

		if (movie == null) {
			return;
		}

		DiscordWebhookInfo info = repository.execute(movie);

		if (info == null) {
			return;
		}

		movie.setMovieDiscord(new MovieDiscord(movie.getId(), info.getMessageId(), movie));
		movieRepository.saveAndFlush(movie);
	}

}
