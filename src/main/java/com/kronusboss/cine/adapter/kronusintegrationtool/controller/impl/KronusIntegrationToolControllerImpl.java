package com.kronusboss.cine.adapter.kronusintegrationtool.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kronusboss.cine.adapter.kronusintegrationtool.controller.KronusIntegrationToolController;
import com.kronusboss.cine.adapter.kronusintegrationtool.controller.dto.MovieSearchResponseDto;
import com.kronusboss.cine.adapter.kronusintegrationtool.controller.dto.MovieSummaryResponseDto;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSearch;
import com.kronusboss.cine.kronusintegrationtool.domain.MovieSummary;
import com.kronusboss.cine.kronusintegrationtool.usecase.MovieSumaryUseCase;
import com.kronusboss.cine.kronusintegrationtool.usecase.SearchMovieTmdbUseCase;

@Controller
public class KronusIntegrationToolControllerImpl implements KronusIntegrationToolController {

	@Autowired
	private MovieSumaryUseCase movieSumaryUseCase;

	@Autowired
	private SearchMovieTmdbUseCase searchMovieTmdbUseCase;

	@Override
	public MovieSummaryResponseDto movieSummary(Long tmdbId) {
		MovieSummary response = movieSumaryUseCase.execute(tmdbId);
		return new MovieSummaryResponseDto(response);
	}

	@Override
	public MovieSearchResponseDto movieSearchByName(String name, String language) {
		MovieSearch response = searchMovieTmdbUseCase.searchByName(name, language);
		return new MovieSearchResponseDto(response);
	}

}
