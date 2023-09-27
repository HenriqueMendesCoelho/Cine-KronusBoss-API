package com.kronusboss.cine.kronusintegrationtool.adapter.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kronusboss.cine.kronusintegrationtool.adapter.controller.KronusIntegrationToolController;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.MovieSearchResponseDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.MovieSummaryResponseDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.WatchProvidersResponseDto;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSearch;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;
import com.kronusboss.cine.kronusintegrationtool.domain.WatchProviders;
import com.kronusboss.cine.kronusintegrationtool.usecase.MovieSumaryUseCase;
import com.kronusboss.cine.kronusintegrationtool.usecase.SearchMovieTmdbUseCase;
import com.kronusboss.cine.kronusintegrationtool.usecase.SearchWatchProviedersUseCase;

@Controller
public class KronusIntegrationToolControllerImpl implements KronusIntegrationToolController {

	@Autowired
	private MovieSumaryUseCase movieSumaryUseCase;

	@Autowired
	private SearchMovieTmdbUseCase searchMovieTmdbUseCase;

	@Autowired
	private SearchWatchProviedersUseCase searchWatchProviedersUseCase;

	@Override
	public MovieSummaryResponseDto movieSummary(Long tmdbId) {
		MovieSummary response = movieSumaryUseCase.execute(tmdbId);
		return new MovieSummaryResponseDto(response);
	}

	@Override
	public MovieSearchResponseDto movieSearchByName(String name, Integer page, String language) {
		MovieSearch response = searchMovieTmdbUseCase.searchByName(name, page, language);
		return new MovieSearchResponseDto(response);
	}

	@Override
	public MovieSearchResponseDto moviesPopular(Integer page) {
		MovieSearch response = searchMovieTmdbUseCase.moviesPopular(page);
		return new MovieSearchResponseDto(response);
	}

	@Override
	public MovieSearchResponseDto moviesNowPlaying(Integer page) {
		MovieSearch response = searchMovieTmdbUseCase.moviesNowPlaying(page);
		return new MovieSearchResponseDto(response);
	}

	@Override
	public MovieSearchResponseDto moviesTopRated(Integer page) {
		MovieSearch response = searchMovieTmdbUseCase.moviesTopRated(page);
		return new MovieSearchResponseDto(response);
	}

	@Override
	public MovieSearchResponseDto moviesRecommendations(Long movieTmdbId, Integer page) {
		MovieSearch response = searchMovieTmdbUseCase.moviesRecommendations(movieTmdbId, page);
		return new MovieSearchResponseDto(response);
	}

	@Override
	public MovieSearchResponseDto moviesSimilar(Long movieTmdbId, Integer page) {
		MovieSearch response = searchMovieTmdbUseCase.moviesSimilar(movieTmdbId, page);
		return new MovieSearchResponseDto(response);
	}

	@Override
	public MovieSearchResponseDto discoverMovies(String sortByParam, Integer page, Integer primaryReleaseYear,
			String withGenres, String withoutGenres) {
		MovieSearch response = searchMovieTmdbUseCase.discoverMovies(sortByParam, page, primaryReleaseYear, withGenres,
				withoutGenres);
		return new MovieSearchResponseDto(response);
	}

	@Override
	public WatchProvidersResponseDto searchWatchProvidersByMovieId(Long movieId) {
		WatchProviders response = searchWatchProviedersUseCase.searchByMovieId(movieId);
		return new WatchProvidersResponseDto(response);
	}

}
