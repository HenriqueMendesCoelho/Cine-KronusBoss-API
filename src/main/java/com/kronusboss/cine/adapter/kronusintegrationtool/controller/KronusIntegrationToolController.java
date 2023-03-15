package com.kronusboss.cine.adapter.kronusintegrationtool.controller;

import com.kronusboss.cine.adapter.kronusintegrationtool.controller.dto.MovieSearchResponseDto;
import com.kronusboss.cine.adapter.kronusintegrationtool.controller.dto.MovieSummaryResponseDto;

public interface KronusIntegrationToolController {

	MovieSummaryResponseDto movieSummary(Long tmdbId);

	MovieSearchResponseDto movieSearchByName(String name, Integer page, String language);
}
