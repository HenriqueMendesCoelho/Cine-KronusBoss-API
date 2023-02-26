package com.kronusboss.cine.kronusintegrationtool.usecase.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest.KronusIntegrationToolRepository;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;
import com.kronusboss.cine.kronusintegrationtool.usecase.MovieSumaryUseCase;

@Component
public class MovieSumarryUseCaseImpl implements MovieSumaryUseCase {

	@Autowired
	private KronusIntegrationToolRepository repository;

	@Override
	public MovieSummary execute(Long tmdbId) {
		return repository.movieSummary(tmdbId);
	}

}
