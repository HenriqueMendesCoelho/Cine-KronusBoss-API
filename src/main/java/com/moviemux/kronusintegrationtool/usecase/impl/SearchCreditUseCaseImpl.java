package com.moviemux.kronusintegrationtool.usecase.impl;

import com.moviemux.kronusintegrationtool.domain.Credit;
import com.moviemux.kronusintegrationtool.usecase.SearchCreditUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moviemux.kronusintegrationtool.adapter.repository.rest.KronusIntegrationToolRepository;

@Component
public class SearchCreditUseCaseImpl implements SearchCreditUseCase {

	@Autowired
	private KronusIntegrationToolRepository repository;

	@Override
	public Credit getMovieCredits(Long movieId) {
		return repository.getCredits(movieId);
	}

}
