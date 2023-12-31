package com.kronusboss.cine.kronusintegrationtool.usecase.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.KronusIntegrationToolRepository;
import com.kronusboss.cine.kronusintegrationtool.domain.Credit;
import com.kronusboss.cine.kronusintegrationtool.usecase.SearchCreditUseCase;

@Component
public class SearchCreditUseCaseImpl implements SearchCreditUseCase {

	@Autowired
	private KronusIntegrationToolRepository repository;

	@Override
	public Credit getMovieCredits(Long movieId) {
		return repository.getCredits(movieId);
	}

}
