package com.moviemux.discord.usecase;

import java.util.UUID;

public interface UpdateMessageWebhookUseCase {
	void updateMovieMessage(UUID movieId);
}
