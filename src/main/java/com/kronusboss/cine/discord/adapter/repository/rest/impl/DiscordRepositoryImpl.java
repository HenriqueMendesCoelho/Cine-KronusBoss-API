package com.kronusboss.cine.discord.adapter.repository.rest.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kronusboss.cine.discord.adapter.repository.rest.DiscordRepository;
import com.kronusboss.cine.discord.adapter.repository.rest.dto.DiscordWebhookRequestDto;
import com.kronusboss.cine.discord.adapter.repository.rest.dto.DiscordWebhookResponseDto;
import com.kronusboss.cine.discord.domain.DiscordWebhookInfo;
import com.kronusboss.cine.movie.domain.Movie;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class DiscordRepositoryImpl implements DiscordRepository {

	@Value("${discord.webhook.execute}")
	private boolean execute;

	@Value("${discord.webhook.update}")
	private boolean update;

	@Value("${discord.webhook.role.id}")
	private String roleId;

	@Autowired
	private WebClient webClientDiscord;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public DiscordWebhookInfo execute(Movie movie) {
		if (!execute) {
			return null;
		}

		DiscordWebhookRequestDto request = new DiscordWebhookRequestDto(movie, roleId);
		try {
			DiscordWebhookResponseDto response = webClientDiscord.post()
					.uri("?wait=true")
					.bodyValue(mapper.writeValueAsString(request))
					.retrieve()
					.bodyToMono(DiscordWebhookResponseDto.class)
					.block();
			return response.toDomain();
		} catch (Exception e) {
			log.error("Error on Discord Webhook create message request raised: ", e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public DiscordWebhookInfo update(Movie movie) {
		if (!update || movie.getMovieDiscord() == null || StringUtils.isBlank(movie.getMovieDiscord().getMessageId())) {
			return null;
		}

		String messageId = movie.getMovieDiscord().getMessageId();
		DiscordWebhookRequestDto request = new DiscordWebhookRequestDto(movie, roleId);
		try {
			DiscordWebhookResponseDto response = webClientDiscord.patch()
					.uri(String.format("/messages/%s?wait=true", messageId))
					.bodyValue(mapper.writeValueAsString(request))
					.retrieve()
					.bodyToMono(DiscordWebhookResponseDto.class)
					.block();
			return response.toDomain();
		} catch (Exception e) {
			log.error("Error on Discord Webhook update message request raised: ", e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

}
