package com.moviemux.statistic.adapter.controller.impl;

import com.moviemux.statistic.adapter.controller.StatisticController;
import com.moviemux.statistic.adapter.controller.dto.MovieStatisticResponseDto;
import com.moviemux.statistic.domain.MovieStatistic;
import com.moviemux.statistic.usecase.MovieStatisticsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
