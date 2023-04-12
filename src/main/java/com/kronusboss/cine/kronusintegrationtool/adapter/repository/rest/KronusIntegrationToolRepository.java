package com.kronusboss.cine.kronusintegrationtool.adapter.repository.rest;

import java.util.List;

import com.kronusboss.cine.kronusintegrationtool.domain.MovieSearch;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;
import com.kronusboss.cine.kronusintegrationtool.domain.SendMailTemplate;
import com.kronusboss.cine.movie.domain.MovieGenre;

public interface KronusIntegrationToolRepository {

	MovieSummary movieSummary(Long tmdbId);

	MovieSearch searchByName(String name, Integer page, String language, boolean includeAdult);

	void sendMailTemplate(SendMailTemplate request);

	List<MovieGenre> listGenres();
}
