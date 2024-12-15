package com.moviemux.kronusintegrationtool.usecase.impl;

import com.moviemux.kronusintegrationtool.domain.WatchProviders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moviemux.kronusintegrationtool.adapter.repository.rest.KronusIntegrationToolRepository;
import com.moviemux.kronusintegrationtool.usecase.SearchWatchProviedersUseCase;

@Component
public class SearchWatchProviedersUseCaseImpl implements SearchWatchProviedersUseCase {

	@Autowired
	private KronusIntegrationToolRepository repository;

	@Override
	public WatchProviders searchByMovieId(Long movieId) {
		return repository.getWatchProviders(movieId);
	}

}
