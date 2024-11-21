package com.moviemux.movie.adapter.repository.rest.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemux.movie.adapter.repository.rest.MovieSocketRespository;
import com.moviemux.movie.adapter.repository.rest.dto.MovieNoteRestRequestDto;
import com.moviemux.movie.domain.MovieNote;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
	public void emitEventMovie(UUID movieId, String event, MovieNote note, String emmitedByUserId) {
		try {
			if (StringUtils.isBlank(event) || movieId == null) {
				return;
			}

			Map<String, Object> map = new HashMap<>();
			map.put("event", event);
			map.put("movie", movieId);
			if (note != null) {
				map.put("content", new MovieNoteRestRequestDto(note));
			}
			if (emmitedByUserId != null) {
				map.put("emmitedByUserId", emmitedByUserId);
			}

			String body = mapper.writeValueAsString(map);
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
