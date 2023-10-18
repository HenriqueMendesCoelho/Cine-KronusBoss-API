package com.kronusboss.cine.statistic.adapter.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.kronusboss.cine.statistic.adapter.controller.StatisticController;
import com.kronusboss.cine.statistic.adapter.controller.dto.MovieStatisticResponseDto;
import com.kronusboss.cine.statistic.domain.MovieStatistic;
import com.kronusboss.cine.statistic.usecase.MovieStatisticsUseCase;

@Controller
public class StatisticControllerImpl implements StatisticController {

	@Autowired
	private MovieStatisticsUseCase movieChartsUseCase;

	@Override
	public MovieStatisticResponseDto charts() {
		MovieStatistic response = movieChartsUseCase.getStatistics();
		return new MovieStatisticResponseDto(response);
	}

}
