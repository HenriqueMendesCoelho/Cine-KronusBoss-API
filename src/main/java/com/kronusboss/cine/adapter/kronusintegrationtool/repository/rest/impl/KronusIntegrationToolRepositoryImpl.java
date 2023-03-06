package com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.KronusIntegrationToolRepository;
import com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.entity.MovieGenreEntity;
import com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.entity.MovieGenresEntity;
import com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.entity.MovieSearchEntity;
import com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.entity.MovieSummaryEntity;
import com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.entity.SendMailTemplateRequestEntity;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSearch;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;
import com.kronusboss.cine.kronusintegrationtool.domain.SendMailTemplate;
import com.kronusboss.cine.movie.domain.MovieGenre;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class KronusIntegrationToolRepositoryImpl implements KronusIntegrationToolRepository {

	@Value("${kit.key}")
	private String APIKEY;

	@Value("${kit.url}")
	private String URL_KIT;

	@Value("${send.mail}")
	private boolean sendMail;

	@Autowired
	ObjectMapper mapper;

	@Override
	public MovieSummary movieSummary(Long tmdbId) {

		RestTemplate template = getRestTemplate();
		String uri = createUri(String.format("/api/v1/movie/%s/summary", tmdbId));

		try {
			ResponseEntity<MovieSummaryEntity> responseEntity = template.exchange(uri, HttpMethod.GET, null,
					MovieSummaryEntity.class);

			MovieSummaryEntity response = responseEntity.getBody();

			return response.toDomain();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RequestRejectedException(e.getMessage());
		}

	}

	@Override
	public MovieSearch searchByName(String name, String language, boolean includeAdult) {
		RestTemplate template = getRestTemplate();
		String uri = createUri(
				String.format("/api/v1/movie?query=%s&language=%s&include_adult=%s", name, language, includeAdult));

		try {
			ResponseEntity<MovieSearchEntity> responseEntity = template.exchange(uri, HttpMethod.GET, null,
					MovieSearchEntity.class);

			MovieSearchEntity response = responseEntity.getBody();

			return response.toDomain();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public void sendMailTemplate(SendMailTemplate request) {

		if (!sendMail) {
			return;
		}

		SendMailTemplateRequestEntity entity = new SendMailTemplateRequestEntity(request);
		RestTemplate template = getRestTemplate();
		String uri = createUri("/api/v1/sendgrid/template");

		HttpEntity<SendMailTemplateRequestEntity> requestHttp = new HttpEntity<SendMailTemplateRequestEntity>(entity);

		try {
			ResponseEntity<Void> response = template.exchange(uri, HttpMethod.POST, requestHttp, Void.class);
			if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is4xxClientError()) {
				log.error(String.format("Error to send mail to: %s", entity.toString()));
				log.error(String.format("Request returns status code: %s", response.getStatusCode()));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			log.error(e.getMessage());
			throw new RequestRejectedException(e.getMessage());
		}

	}

	@Override
	public List<MovieGenre> listGenres() {
		RestTemplate template = getRestTemplate();
		String uri = createUri("/api/v1/genre/movie/list");

		try {
			ResponseEntity<MovieGenresEntity> responseEntity = template.exchange(uri, HttpMethod.GET, null,
					MovieGenresEntity.class);

			MovieGenresEntity response = responseEntity.getBody();
			List<MovieGenreEntity> genres = response.getGenres();

			return genres.stream().map(MovieGenreEntity::toDomain).collect(Collectors.toList());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RequestRejectedException(e.getMessage());
		}
	}

	private String createUri(String path) {
		String[] schemeHost = URL_KIT.split("://");
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme(schemeHost[0])
				.host(schemeHost[1])
				.path(path)
				.build();
		return uri.toString();
	}

	private RestTemplate getRestTemplate() {
		ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
			request.getHeaders().add("Authorization", String.format("Bearer %s", APIKEY));
			return execution.execute(request, body);
		};

		RestTemplate restTemplate = new RestTemplateBuilder().interceptors(interceptor).build();
		return restTemplate;
	}

}
