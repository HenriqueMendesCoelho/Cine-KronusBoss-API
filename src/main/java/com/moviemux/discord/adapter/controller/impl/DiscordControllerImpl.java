package com.moviemux.discord.adapter.controller.impl;

import java.util.UUID;

import com.moviemux.discord.adapter.controller.DiscordController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.moviemux.discord.usecase.SendMessageWebhookUseCase;
import com.moviemux.discord.usecase.UpdateMessageWebhookUseCase;

@Controller
public class DiscordControllerImpl implements DiscordController {

	@Autowired
	private SendMessageWebhookUseCase sendMessageWebhook;

	@Autowired
	private UpdateMessageWebhookUseCase updateMessageWebhook;

	@Override
	public void execute(UUID movieId) {
		sendMessageWebhook.sendMovieMessage(movieId);
	}

	@Override
	public void update(UUID movieId) {
		updateMessageWebhook.updateMovieMessage(movieId);
	}

}
