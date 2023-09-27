package com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.KronusIntegrationToolRepository;
import com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.dto.MovieGenreResponseDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.dto.MovieSearchResponseDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.dto.MovieSummaryResponseDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.dto.SendMailTemplateRequestDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.dto.WatchProvidersResponseDto;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSearch;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;
import com.kronusboss.cine.kronusintegrationtool.domain.SendMailTemplate;
import com.kronusboss.cine.kronusintegrationtool.domain.WatchProviders;
import com.kronusboss.cine.movie.domain.MovieGenre;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class KronusIntegrationToolRepositoryImpl implements KronusIntegrationToolRepository {

	@Value("${send.mail}")
	private boolean sendMail;

	@Autowired
	private WebClient webClientKit;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public MovieSummary movieSummary(Long tmdbId) {
		String uri = "/api/v1/tmdb/movie/%s/summary".formatted(tmdbId);
		try {
			MovieSummaryResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSummaryResponseDto.class)
					.block();

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}

	}

	@Override
	public MovieSearch searchByName(String name, Integer page, String language, boolean includeAdult) {
		String uri = "/api/v1/tmdb/search/movie?query=%s&page=%s&language=%s&include_adult=%s".formatted(name, page,
				language, includeAdult);
		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public void sendMailTemplate(SendMailTemplate mail) {
		try {
			if (!sendMail) {
				return;
			}

			SendMailTemplateRequestDto request = new SendMailTemplateRequestDto(mail);
			webClientKit.post()
					.uri("/api/v1/sendgrid/template")
					.bodyValue(mapper.writeValueAsString(request))
					.retrieve()
					.bodyToMono(Void.class)
					.block();
		} catch (Exception e) {
			log.error("Error to send mail:", e);
			throw new RequestRejectedException(e.getMessage());
		}

	}

	@Override
	public MovieSearch moviesPopular(Integer page) {
		String uri = "/api/v1/tmdb/movie/popular?page=%s&language=%s&region=%s".formatted(page, "pt-Br", "BR");
		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public MovieSearch moviesNowPlaying(Integer page) {
		String uri = "/api/v1/tmdb/movie/now_playing?page=%s&language=%s&region=%s".formatted(page, "pt-Br", "BR");
		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public MovieSearch moviesTopRated(Integer page) {
		String uri = "/api/v1/tmdb/movie/top_rated?page=%s&language=%s&region=%s".formatted(page, "pt-Br", "BR");
		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public MovieSearch moviesRecommendations(Long movieTmdbId, Integer page) {
		String uri = "/api/v1/tmdb/movie/%s/recommendations?page=%s&language=%s".formatted(movieTmdbId, page, "pt-Br");
		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public MovieSearch moviesSimilar(Long movieTmdbId, Integer page) {
		String uri = "/api/v1/tmdb/movie/%s/similar?page=%s&language=%s".formatted(movieTmdbId, page, "pt-Br");
		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public MovieSearch discoverMovies(String sortByParam, Integer page, Integer primaryReleaseYear, String withGenres,
			String withoutGenres) {
		StringBuilder uri = new StringBuilder(
				"/api/v1/tmdb/discover/movie?page=%s&language=%s&include_adult=false&vote_count.gte=300".formatted(page,
						"pt-Br"));

		if (sortByParam != null) {
			uri.append("&sort_by=%s".formatted(sortByParam));
		}

		if (primaryReleaseYear != null) {
			uri.append("&primary_release_year=%s".formatted(primaryReleaseYear));
		}

		if (withGenres != null) {
			uri.append("&with_genres=%s".formatted(withGenres));
		}

		if (withoutGenres != null) {
			uri.append("&without_genres=%s".formatted(withoutGenres));
		}

		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri.toString())
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public List<MovieGenre> listGenres() {
		String uri = "/api/v1/tmdb/genre/movie/list?language=%s".formatted("pt-Br");
		try {
			List<MovieGenreResponseDto> response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToFlux(MovieGenreResponseDto.class)
					.collectList()
					.block();

			return response.stream().map(MovieGenreResponseDto::toDomain).collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public WatchProviders getWatchProviders(Long tmdbId) {
		String uri = "/api/v1/tmdb/movie/%s/watch/providers".formatted(tmdbId);
		try {
			WatchProvidersResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(WatchProvidersResponseDto.class)
					.block();

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

}
