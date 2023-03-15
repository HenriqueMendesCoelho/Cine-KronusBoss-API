package com.kronusboss.cine.kronusintegrationtool.usecase;

import com.kronusboss.cine.kronusintegrationtool.domain.MovieSearch;

public interface SearchMovieTmdbUseCase {

	MovieSearch searchByName(String name, Integer page, String language);

}
