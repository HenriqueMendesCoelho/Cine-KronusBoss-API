package com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest;

import java.util.List;

import com.kronusboss.cine.kronusintegrationtool.domain.Credit;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSearch;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;
import com.kronusboss.cine.kronusintegrationtool.domain.SendMailTemplate;
import com.kronusboss.cine.kronusintegrationtool.domain.WatchProviders;
import com.kronusboss.cine.movie.domain.MovieGenre;

public interface KronusIntegrationToolRepository {

	MovieSummary movieSummary(Long tmdbId);

	MovieSearch searchByName(String name, Integer page, String language, boolean includeAdult);

	MovieSearch moviesPopular(Integer page);

	MovieSearch moviesNowPlaying(Integer page);

	MovieSearch moviesTopRated(Integer page);

	MovieSearch moviesRecommendations(Long movieTmdbId, Integer page);

	MovieSearch moviesSimilar(Long movieTmdbId, Integer page);

	WatchProviders getWatchProviders(Long tmdbId);

	MovieSearch discoverMovies(String sortByParam, Integer page, Integer primaryReleaseYear, String with_genres,
			String without_genres);

	void sendMailTemplate(SendMailTemplate request);

	List<MovieGenre> listGenres();

	Credit getCredits(Long tmdbId);
}
