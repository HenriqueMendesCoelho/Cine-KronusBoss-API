package com.kronusboss.cine.discord.adapter.repository.rest.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.kronusboss.cine.discord.adapter.repository.rest.DiscordRepository;
import com.kronusboss.cine.discord.adapter.repository.rest.dto.DiscordWebhookRequestDto;
import com.kronusboss.cine.discord.adapter.repository.rest.dto.DiscordWebhookResponseDto;
import com.kronusboss.cine.discord.domain.DiscordWebhookInfo;
import com.kronusboss.cine.movie.domain.Movie;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class DiscordRepositoryImpl implements DiscordRepository {

	@Value("${discord.webhook.url}")
	private String webhookUrl;

	@Value("${discord.webhook.execute}")
	private boolean execute;

	@Value("${discord.webhook.update}")
	private boolean update;

	@Value("${discord.webhook.role.id}")
	private String roleId;

	@Override
	public DiscordWebhookInfo execute(Movie movie) {
		if (!execute) {
			return null;
		}

		DiscordWebhookRequestDto entity = new DiscordWebhookRequestDto(movie, roleId);
		RestTemplate template = new RestTemplate();
		String uri = createUri();

		HttpEntity<DiscordWebhookRequestDto> requestHttp = new HttpEntity<DiscordWebhookRequestDto>(entity);

		try {
			ResponseEntity<DiscordWebhookResponseDto> response = template.exchange(uri, HttpMethod.POST, requestHttp,
					DiscordWebhookResponseDto.class);
			if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
				log.error(String.format("Error to execute discord webhook: %s", entity.toString()));
				log.error(String.format("Discord returns status code: %s", response.getStatusCode()));
			}
			return response.getBody().toDomain();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public DiscordWebhookInfo update(Movie movie) {
		if (!update || movie.getMovieDiscord() == null || movie.getMovieDiscord().getMessageId().isEmpty()) {
			return null;
		}

		DiscordWebhookRequestDto entity = new DiscordWebhookRequestDto(movie, roleId);
		RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		String uri = createUri(movie.getMovieDiscord().getMessageId());

		HttpEntity<DiscordWebhookRequestDto> requestHttp = new HttpEntity<DiscordWebhookRequestDto>(entity);

		try {
			ResponseEntity<DiscordWebhookResponseDto> response = template.exchange(uri, HttpMethod.PATCH, requestHttp,
					DiscordWebhookResponseDto.class);
			if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
				log.error(String.format("Error to execute discord webhook: %s", entity.toString()));
				log.error(String.format("Discord returns status code: %s", response.getStatusCode()));
			}
			return response.getBody().toDomain();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RequestRejectedException(e.getMessage());
		}
	}

	private String createUri() {
		String[] urlSplited = splitWebhook();
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme(urlSplited[0])
				.host(urlSplited[1])
				.path(urlSplited[2] + "?wait=true")
				.build();

		return uri.toString();
	}

	private String createUri(String messageId) {
		String[] urlSplited = splitWebhook();
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme(urlSplited[0])
				.host(urlSplited[1])
				.path(String.format("%s/messages/%s", urlSplited[2], messageId))
				.build();

		return uri.toString();
	}

	private String[] splitWebhook() {
		String[] urlWithoutPath = webhookUrl.split("/api/");
		String[] schemeAndHost = urlWithoutPath[0].split("://");
		String[] result = { schemeAndHost[0], schemeAndHost[1], String.format("/api/%s", urlWithoutPath[1]) };

		return result;
	}

}
