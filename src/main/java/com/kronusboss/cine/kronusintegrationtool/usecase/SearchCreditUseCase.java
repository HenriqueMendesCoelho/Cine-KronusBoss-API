package com.kronusboss.cine.kronusintegrationtool.usecase;

import com.kronusboss.cine.kronusintegrationtool.domain.Credit;

public interface SearchCreditUseCase {

	Credit getMovieCredits(Long movieId);

}
