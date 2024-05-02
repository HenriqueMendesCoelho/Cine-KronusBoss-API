package com.kronusboss.cine.kronusintegrationtool.usecase;

import com.kronusboss.cine.kronusintegrationtool.domain.MovieSearch;

public interface SearchMovieTmdbUseCase {

	MovieSearch searchByName(String name, Integer page, String language);

	MovieSearch moviesPopular(Integer page);

	MovieSearch moviesNowPlaying(Integer page);

	MovieSearch moviesUpcoming(Integer page);

	MovieSearch moviesTopRated(Integer page);

	MovieSearch moviesRecommendations(Long movieTmdbId, Integer page);

	MovieSearch moviesSimilar(Long movieTmdbId, Integer page);

	MovieSearch discoverMovies(String sortByParam, Integer page, Integer primaryReleaseYear, String withGenres,
			String withoutGenres);

}
