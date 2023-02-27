package com.kronusboss.cine.adapter.kronusintegrationtool.repository.rest;

import com.kronusboss.cine.kronusintegrationtool.domain.MovieSearch;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;
import com.kronusboss.cine.kronusintegrationtool.domain.SendMailTemplate;

public interface KronusIntegrationToolRepository {

	MovieSummary movieSummary(Long tmdbId);

	MovieSearch searchByName(String name, String language, boolean includeAdult);

	void sendMailTemplate(SendMailTemplate request);
}
