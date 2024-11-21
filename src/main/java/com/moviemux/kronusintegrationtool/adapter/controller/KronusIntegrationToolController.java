package com.moviemux.kronusintegrationtool.adapter.controller;

import com.moviemux.kronusintegrationtool.adapter.controller.dto.*;

public interface KronusIntegrationToolController {

	MovieSummaryResponseDto movieSummary(Long tmdbId);

	MovieInfoResponseDto movieInfo(Long tmdbId);

	MovieSearchResponseDto movieSearchByName(String name, Integer page, String language);

	MovieSearchResponseDto moviesPopular(Integer page);

	MovieSearchResponseDto moviesNowPlaying(Integer page);

	MovieSearchResponseDto moviesUpcoming(Integer page);

	MovieSearchResponseDto moviesTopRated(Integer page);

	MovieSearchResponseDto moviesRecommendations(Long movieTmdbId, Integer page);

	MovieSearchResponseDto moviesSimilar(Long movieTmdbId, Integer page);

	MovieSearchResponseDto discoverMovies(String sortByParam, Integer page, Integer primaryReleaseYear,
			String withGenres, String withoutGenres);

	WatchProvidersResponseDto searchWatchProvidersByMovieId(Long movieId);

	CreditResponseDto searchMovieCredits(Long movieId);
}
