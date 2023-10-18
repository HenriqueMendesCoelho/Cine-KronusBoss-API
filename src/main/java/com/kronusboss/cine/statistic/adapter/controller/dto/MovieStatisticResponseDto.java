package com.kronusboss.cine.statistic.adapter.controller.dto;

import java.util.Map;

import com.kronusboss.cine.statistic.domain.MovieStatistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieStatisticResponseDto {

	private Integer totalNumberOfMovies;
	private Double averageRate;
	private Integer averageRuntime;
	private Map<String, Double> moviesByGender;
	private Map<String, Long> moviesSixMonthsAgo;

	public MovieStatisticResponseDto(MovieStatistic statistic) {
		totalNumberOfMovies = statistic.getTotalNumberOfMovies();
		averageRate = statistic.getAverageRate();
		averageRuntime = statistic.getAverageRuntime();
		moviesByGender = statistic.getMoviesByGender();
		moviesSixMonthsAgo = statistic.getMoviesSixMonthsAgo();
	}
}
