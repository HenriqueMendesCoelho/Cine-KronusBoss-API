package com.moviemux.discord.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moviemux.discord.adapter.repository.rest.DiscordRepository;
import com.moviemux.discord.usecase.UpdateMessageWebhookUseCase;
import com.moviemux.movie.adapter.repository.MovieRepository;
import com.moviemux.movie.domain.Movie;

@Component
public class UpdateMessageWebhookUseCaseImpl implements UpdateMessageWebhookUseCase {

	@Autowired
	private DiscordRepository repository;

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public void updateMovieMessage(UUID movieId) {
		Movie movie = movieRepository.findById(movieId).orElse(null);

		if (movie == null || !movie.isShowNotes()) {
			return;
		}

		repository.update(movie);
	}

}
