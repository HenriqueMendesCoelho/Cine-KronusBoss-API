package com.moviemux.discord.adapter.repository.rest;

import com.moviemux.discord.domain.DiscordWebhookInfo;
import com.moviemux.movie.domain.Movie;

public interface DiscordRepository {

	DiscordWebhookInfo execute(Movie movie);

	DiscordWebhookInfo update(Movie movie);
}
