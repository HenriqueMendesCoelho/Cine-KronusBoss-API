package com.kronusboss.cine.kronusintegrationtool.usecase;

import com.kronusboss.cine.kronusintegrationtool.domain.WatchProviders;

public interface SearchWatchProviedersUseCase {

	WatchProviders searchByMovieId(Long movieId);

}
