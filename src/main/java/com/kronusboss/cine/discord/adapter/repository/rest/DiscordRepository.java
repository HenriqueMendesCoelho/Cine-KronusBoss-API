package com.kronusboss.cine.discord.adapter.repository.rest;

import com.kronusboss.cine.discord.domain.DiscordWebhookInfo;
import com.kronusboss.cine.movie.domain.Movie;

public interface DiscordRepository {

	DiscordWebhookInfo execute(Movie movie);

	DiscordWebhookInfo update(Movie movie);
}
