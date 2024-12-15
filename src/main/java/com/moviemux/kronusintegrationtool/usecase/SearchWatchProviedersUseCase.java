package com.moviemux.kronusintegrationtool.usecase;

import com.moviemux.kronusintegrationtool.domain.WatchProviders;

public interface SearchWatchProviedersUseCase {

	WatchProviders searchByMovieId(Long movieId);

}
