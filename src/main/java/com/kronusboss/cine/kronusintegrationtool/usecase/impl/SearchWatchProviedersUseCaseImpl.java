package com.kronusboss.cine.kronusintegrationtool.usecase.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest.KronusIntegrationToolRepository;
import com.kronusboss.cine.kronusintegrationtool.domain.WatchProviders;
import com.kronusboss.cine.kronusintegrationtool.usecase.SearchWatchProviedersUseCase;

@Component
public class SearchWatchProviedersUseCaseImpl implements SearchWatchProviedersUseCase {

	@Autowired
	private KronusIntegrationToolRepository repository;

	@Override
	public WatchProviders searchByMovieId(Long movieId) {
		return repository.getWatchProviders(movieId);
	}

}
