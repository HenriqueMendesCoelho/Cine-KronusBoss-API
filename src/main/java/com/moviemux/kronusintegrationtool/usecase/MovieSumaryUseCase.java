package com.moviemux.kronusintegrationtool.usecase;

import com.moviemux.kronusintegrationtool.domain.MovieSummary;

public interface MovieSumaryUseCase {

	MovieSummary execute(Long tmdbId);

}
