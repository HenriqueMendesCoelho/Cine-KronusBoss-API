package com.kronusboss.cine.kronusintegrationtool.adapter.controller;

import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.MovieSearchResponseDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.MovieSummaryResponseDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.WatchProvidersResponseDto;

public interface KronusIntegrationToolController {

	MovieSummaryResponseDto movieSummary(Long tmdbId);

	MovieSearchResponseDto movieSearchByName(String name, Integer page, String language);

	MovieSearchResponseDto moviesPopular(Integer page);

	MovieSearchResponseDto moviesNowPlaying(Integer page);

	MovieSearchResponseDto moviesTopRated(Integer page);

	MovieSearchResponseDto moviesRecommendations(Long movieTmdbId, Integer page);

	MovieSearchResponseDto moviesSimilar(Long movieTmdbId, Integer page);

	MovieSearchResponseDto discoverMovies(String sortByParam, Integer page, Integer primaryReleaseYear,
			String withGenres, String withoutGenres);

	WatchProvidersResponseDto searchWatchProvidersByMovieId(Long movieId);

}
