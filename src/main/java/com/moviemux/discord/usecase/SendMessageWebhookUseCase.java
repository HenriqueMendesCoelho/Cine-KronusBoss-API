package com.moviemux.discord.usecase;

import java.util.UUID;

public interface SendMessageWebhookUseCase {
	void sendMovieMessage(UUID movieId);
}
