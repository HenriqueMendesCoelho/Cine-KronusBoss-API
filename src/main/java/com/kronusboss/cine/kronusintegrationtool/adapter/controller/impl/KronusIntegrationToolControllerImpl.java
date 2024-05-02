package com.kronusboss.cine.kronusintegrationtool.adapter.controller.impl;

import com.kronusboss.cine.kronusintegrationtool.adapter.controller.KronusIntegrationToolController;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.*;
import com.kronusboss.cine.kronusintegrationtool.domain.Credit;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSearch;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;
import com.kronusboss.cine.kronusintegrationtool.domain.WatchProviders;
import com.kronusboss.cine.kronusintegrationtool.usecase.MovieSumaryUseCase;
import com.kronusboss.cine.kronusintegrationtool.usecase.SearchCreditUseCase;
import com.kronusboss.cine.kronusintegrationtool.usecase.SearchMovieTmdbUseCase;
import com.kronusboss.cine.kronusintegrationtool.usecase.SearchWatchProviedersUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class KronusIntegrationToolControllerImpl implements KronusIntegrationToolController {

	@Autowired
	private MovieSumaryUseCase movieSumaryUseCase;

	@Autowired
	private SearchMovieTmdbUseCase searchMovieTmdbUseCase;

	@Autowired
	private SearchWatchProviedersUseCase searchWatchProviedersUseCase;

	@Autowired
	private SearchCreditUseCase searchCreditUseCase;

	@Override
	public MovieSummaryResponseDto movieSummary(Long tmdbId) {
		MovieSummary response = movieSumaryUseCase.execute(tmdbId);
		return new MovieSummaryResponseDto(response);
	}

	@Override
	public MovieInfoResponseDto movieInfo(Long tmdbId) {
		MovieSummary response = movieSumaryUseCase.execute(tmdbId);
		return new MovieInfoResponseDto(response);
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
	public MovieSearchResponseDto moviesUpcoming(Integer page) {
		MovieSearch response = searchMovieTmdbUseCase.moviesUpcoming(page);
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

	@Override
	public CreditResponseDto searchMovieCredits(Long movieId) {
		Credit response = searchCreditUseCase.getMovieCredits(movieId);
		return new CreditResponseDto(response);
	}

}
