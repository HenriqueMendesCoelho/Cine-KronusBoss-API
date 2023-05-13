package com.kronusboss.cine.discord.adapter.controller.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kronusboss.cine.discord.adapter.controller.DiscordController;
import com.kronusboss.cine.discord.usecase.SendMessageWebhookUseCase;
import com.kronusboss.cine.discord.usecase.UpdateMessageWebhookUseCase;

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
