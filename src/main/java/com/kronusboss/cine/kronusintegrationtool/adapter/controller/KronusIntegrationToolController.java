package com.kronusboss.cine.kronusintegrationtool.adapter.controller;

import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.MovieSearchResponseDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.MovieSummaryResponseDto;

public interface KronusIntegrationToolController {

	MovieSummaryResponseDto movieSummary(Long tmdbId);

	MovieSearchResponseDto movieSearchByName(String name, Integer page, String language);
}
