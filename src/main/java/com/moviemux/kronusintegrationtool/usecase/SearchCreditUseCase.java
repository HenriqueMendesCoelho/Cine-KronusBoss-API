package com.moviemux.kronusintegrationtool.usecase;

import com.moviemux.kronusintegrationtool.domain.Credit;

public interface SearchCreditUseCase {

	Credit getMovieCredits(Long movieId);

}
