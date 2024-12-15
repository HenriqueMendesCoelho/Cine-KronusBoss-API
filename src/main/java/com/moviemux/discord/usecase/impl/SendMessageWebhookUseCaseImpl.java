package com.moviemux.discord.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moviemux.discord.adapter.repository.rest.DiscordRepository;
import com.moviemux.discord.domain.DiscordWebhookInfo;
import com.moviemux.discord.usecase.SendMessageWebhookUseCase;
import com.moviemux.movie.adapter.repository.MovieRepository;
import com.moviemux.movie.domain.Movie;
import com.moviemux.movie.domain.MovieDiscord;

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
