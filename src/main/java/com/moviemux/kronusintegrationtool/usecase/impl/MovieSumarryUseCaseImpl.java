package com.moviemux.kronusintegrationtool.usecase.impl;

import com.moviemux.kronusintegrationtool.domain.MovieSummary;
import com.moviemux.kronusintegrationtool.usecase.MovieSumaryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moviemux.kronusintegrationtool.adapter.repository.rest.KronusIntegrationToolRepository;

@Component
public class MovieSumarryUseCaseImpl implements MovieSumaryUseCase {

	@Autowired
	private KronusIntegrationToolRepository repository;

	@Override
	public MovieSummary execute(Long tmdbId) {
		return repository.movieSummary(tmdbId);
	}

}
