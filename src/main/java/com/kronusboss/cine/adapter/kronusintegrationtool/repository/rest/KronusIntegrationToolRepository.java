package com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest;

import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;

public interface KronusIntegrationToolRepository {

	MovieSummary movieSummary(Long tmdbId);

}
