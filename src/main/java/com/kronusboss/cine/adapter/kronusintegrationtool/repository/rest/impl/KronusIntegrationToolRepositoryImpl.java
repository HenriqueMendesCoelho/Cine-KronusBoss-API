package com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.KronusIntegrationToolRepository;
import com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.entity.MovieSummaryEntity;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;

@Repository
public class KronusIntegrationToolRepositoryImpl implements KronusIntegrationToolRepository {

	@Value("${kit.key}")
	private String apikey;

	@Value("${kit.url}")
	private String URL_KIT;

	@Override
	public MovieSummary movieSummary(Long tmdbId) {

		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String uri = createUri(String.format("/api/v1/movie/%s/summary", tmdbId));

		headers.add("Authorization", String.format("Bearer %s", apikey));
		HttpEntity<String> header = new HttpEntity<>(headers);

		ResponseEntity<MovieSummaryEntity> responseEntity = template.exchange(uri, HttpMethod.GET, header,
				MovieSummaryEntity.class);

		MovieSummaryEntity response = responseEntity.getBody();

		return response.toDomain();
	}

	private String createUri(String path) {
		String[] schemeHost = URL_KIT.split("://");
		UriComponents uri = UriComponentsBuilder.newInstance().scheme(schemeHost[0]).host(schemeHost[1]).path(path)
				.build();
		return uri.toString();
	}

}
