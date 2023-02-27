package com.kronusboss.cine.kronusintegrationtool.usecase.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.KronusIntegrationToolRepository;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSearch;
import com.kronusboss.cine.kronusintegrationtool.usecase.SearchMovieTmdbUseCase;

@Component
public class SearchMovieTmdbUseCaseImpl implements SearchMovieTmdbUseCase {

	@Autowired
	private KronusIntegrationToolRepository repository;

	@Override
	public MovieSearch searchByName(String name, String language) {
		return repository.searchByName(name, language, false);
	}

}
