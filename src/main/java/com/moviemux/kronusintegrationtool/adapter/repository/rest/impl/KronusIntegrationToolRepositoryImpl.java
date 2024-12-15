package com.moviemux.kronusintegrationtool.adapter.repository.rest.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviemux.kronusintegrationtool.adapter.repository.rest.KronusIntegrationToolRepository;
import com.moviemux.kronusintegrationtool.adapter.repository.rest.dto.*;
import com.moviemux.kronusintegrationtool.domain.*;
import com.moviemux.movie.domain.MovieGenre;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

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
		final String uri = "/api/v1/tmdb/movie/%s/summary".formatted(tmdbId);
		try {
			MovieSummaryResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSummaryResponseDto.class)
					.block();

			assert response != null;
			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}

	}

	@Override
	public MovieSearch searchByName(String name, Integer page, String language, boolean includeAdult) {
		final String uri = "/api/v1/tmdb/search/movie?query=%s&page=%s&language=%s&include_adult=%s".formatted(name,
				page, language, includeAdult);
		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();
			assert response != null;

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
		final String uri = "/api/v1/tmdb/movie/popular?page=%s&language=%s&region=%s".formatted(page, "pt-Br", "BR");
		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();
			assert response != null;

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public MovieSearch moviesNowPlaying(Integer page) {
		final String uri = "/api/v1/tmdb/movie/now_playing?page=%s&language=%s&region=%s".formatted(page, "pt-Br",
				"BR");
		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();
			assert response != null;

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public MovieSearch moviesUpcoming(Integer page) {
		final String uri = "/api/v1/tmdb/movie/upcoming?page=%s&language=%s&region=%s".formatted(page, "pt-Br", "BR");
		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();
			assert response != null;

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public MovieSearch moviesTopRated(Integer page) {
		final String uri = "/api/v1/tmdb/movie/top_rated?page=%s&language=%s&region=%s".formatted(page, "pt-Br", "BR");
		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();
			assert response != null;

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public MovieSearch moviesRecommendations(Long movieTmdbId, Integer page) {
		final String uri = "/api/v1/tmdb/movie/%s/recommendations?page=%s&language=%s".formatted(movieTmdbId, page,
				"pt-Br");
		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();
			assert response != null;

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public MovieSearch moviesSimilar(Long movieTmdbId, Integer page) {
		final String uri = "/api/v1/tmdb/movie/%s/similar?page=%s&language=%s".formatted(movieTmdbId, page, "pt-Br");
		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();
			assert response != null;

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public MovieSearch discoverMovies(String sortByParam, Integer page, Integer primaryReleaseYear, String withGenres,
			String withoutGenres) {
		final StringBuilder uri = new StringBuilder(
				"/api/v1/tmdb/discover/movie?page=%s&language=%s&include_adult=false&vote_count.gte=300".formatted(page,
						"pt-Br"));

		if (StringUtils.isNotEmpty(sortByParam)) {
			uri.append("&sort_by=%s".formatted(sortByParam));
		}

		if (primaryReleaseYear != null) {
			uri.append("&primary_release_year=%s".formatted(primaryReleaseYear));
		}

		if (StringUtils.isNotEmpty(withGenres)) {
			uri.append("&with_genres=%s".formatted(withGenres));
		}

		if (StringUtils.isNotEmpty(withoutGenres)) {
			uri.append("&without_genres=%s".formatted(withoutGenres));
		}

		try {
			MovieSearchResponseDto response = webClientKit.get()
					.uri(uri.toString())
					.retrieve()
					.bodyToMono(MovieSearchResponseDto.class)
					.block();
			assert response != null;

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public List<MovieGenre> listGenres() {
		final String uri = "/api/v1/tmdb/genre/movie/list?language=%s".formatted("pt-Br");
		try {
			List<MovieGenreResponseDto> response = webClientKit.get()
					.uri(uri)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(MovieGenresResponseDto.class)
					.flatMapMany(r -> Flux.fromIterable(r.getGenres()))
					.collectList()
					.block();
			assert response != null;

			return response.stream().map(MovieGenreResponseDto::toDomain).collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public WatchProviders getWatchProviders(Long tmdbId) {
		final String uri = "/api/v1/tmdb/movie/%s/watch/providers".formatted(tmdbId);
		try {
			WatchProvidersResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(WatchProvidersResponseDto.class)
					.block();
			assert response != null;

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

	@Override
	public Credit getCredits(Long tmdbId) {
		final String uri = "/api/v1/tmdb/movie/%s/credits".formatted(tmdbId);
		try {
			CreditResponseDto response = webClientKit.get()
					.uri(uri)
					.retrieve()
					.bodyToMono(CreditResponseDto.class)
					.block();
			assert response != null;

			return response.toDomain();
		} catch (Exception e) {
			log.error("Error with KIT API request at %s".formatted(uri), e);
			throw new RequestRejectedException(e.getMessage());
		}
	}

}
