package com.kronusboss.cine.user.adapter.controller.dto;

import com.kronusboss.cine.user.domain.Statistics;

import lombok.Data;

@Data
public class StatisticsResponseAdmDto {

	private int ratingsGiven;
	private int registeredMovies;
	private int consecutiveFailedLoginAttempts;
	private int theoreticalTotalMinutesWatched;

	public StatisticsResponseAdmDto(Statistics statistics) {
		ratingsGiven = statistics.getRatingsGiven();
		registeredMovies = statistics.getRegisteredMovies();
		consecutiveFailedLoginAttempts = statistics.getConsecutiveFailedLoginAttempts();
		theoreticalTotalMinutesWatched = statistics.getTheoreticalTotalMinutesWatched();
	}

}
