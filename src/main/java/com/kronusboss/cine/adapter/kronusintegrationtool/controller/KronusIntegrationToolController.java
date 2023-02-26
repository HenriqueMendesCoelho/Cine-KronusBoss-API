package com.kronusboss.cine.adapter.kronusintegrationtool.controller;

import com.kronusboss.cine.adapter.kronusintegrationtool.controller.dto.MovieSummaryResponseDto;

public interface KronusIntegrationToolController {

	MovieSummaryResponseDto movieSummary(Long tmdbId);

}
