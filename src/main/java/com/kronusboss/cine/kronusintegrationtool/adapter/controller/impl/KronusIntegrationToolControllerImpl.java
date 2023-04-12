package com.kronusboss.cine.kronusintegrationtool.adapter.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kronusboss.cine.kronusintegrationtool.adapter.controller.KronusIntegrationToolController;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.MovieSearchResponseDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.MovieSummaryResponseDto;
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
	public MovieSearchResponseDto movieSearchByName(String name, Integer page, String language) {
		MovieSearch response = searchMovieTmdbUseCase.searchByName(name, page, language);
		return new MovieSearchResponseDto(response);
	}

}
