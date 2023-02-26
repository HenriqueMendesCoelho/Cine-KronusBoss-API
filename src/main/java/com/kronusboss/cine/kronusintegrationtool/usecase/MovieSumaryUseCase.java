package com.kronusboss.cine.kronusintegrationtool.usecase;

import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;

public interface MovieSumaryUseCase {

	MovieSummary execute(Long tmdbId);

}
