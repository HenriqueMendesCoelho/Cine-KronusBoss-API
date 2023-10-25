package com.kronusboss.cine.movie.adapter.repository.rest.impl;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kronusboss.cine.movie.adapter.repository.rest.MovieSocketRespository;
import com.kronusboss.cine.movie.adapter.repository.rest.dto.MovieNoteRestRequestDto;
import com.kronusboss.cine.movie.domain.MovieNote;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MovieSocketRestRespositoryImpl implements MovieSocketRespository {

	@Autowired
	private WebClient webClientSocketApi;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public void emitAllMoviesEvent(String event) {
		try {
			if (StringUtils.isBlank(event)) {
				event = "update";
			}

			webClientSocketApi.post()
					.uri("/api/v1/movie/all/emit/%s".formatted(event))
					.bodyValue(mapper.writeValueAsString(Map.of("event", event)))
					.retrieve()
					.bodyToMono(Void.class)
					.block();
		} catch (Exception e) {
			log.error("Error on Socket Api emit event all movies:", e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public void emitEventMovie(UUID movieId, String event, MovieNote note) {
		try {
			if (StringUtils.isBlank(event) || movieId == null) {
				return;
			}

			String body = note == null ? mapper.writeValueAsString(Map.of("event", event, "movie", movieId))
					: mapper.writeValueAsString(
							Map.of("event", event, "movie", movieId, "content", new MovieNoteRestRequestDto(note)));
			webClientSocketApi.post()
					.uri("/api/v1/movie/%s/note/emit/%s".formatted(movieId, event))
					.bodyValue(body)
					.retrieve()
					.bodyToMono(Void.class)
					.block();
		} catch (Exception e) {
			log.error("Error on Socket Api emit event movie:", e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

}
