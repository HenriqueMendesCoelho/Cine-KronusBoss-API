package com.kronusboss.cine.movie.adapter.repository.rest.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kronusboss.cine.movie.adapter.repository.rest.MovieSocketRespository;

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

}
