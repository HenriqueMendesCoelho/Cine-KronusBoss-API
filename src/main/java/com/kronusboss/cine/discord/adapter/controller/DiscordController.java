package com.kronusboss.cine.discord.adapter.controller;

import java.util.UUID;

public interface DiscordController {
	void execute(UUID movieId);

	void update(UUID movieId);
}
