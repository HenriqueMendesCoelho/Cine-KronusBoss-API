package com.kronusboss.cine.user.adapter.controller.dto;

import com.kronusboss.cine.user.domain.Statistics;

import lombok.Data;

@Data
public class StatisticsResponseDto {

	private int ratingsGiven;
	private int registeredMovies;
	private int theoreticalTotalMinutesWatched;

	public StatisticsResponseDto(Statistics statistics) {
		ratingsGiven = statistics.getRatingsGiven();
		registeredMovies = statistics.getRegisteredMovies();
		theoreticalTotalMinutesWatched = statistics.getTheoreticalTotalMinutesWatched();
	}

}
