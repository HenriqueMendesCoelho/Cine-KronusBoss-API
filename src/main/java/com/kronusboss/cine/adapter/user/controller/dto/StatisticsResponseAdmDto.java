package com.kronusboss.cine.adapter.user.controller.dto;

import com.kronusboss.cine.user.domain.Statistics;

import lombok.Data;

@Data
public class StatisticsResponseAdmDto {

	private int ratingsGiven;
	private int registeredMovies;
	private int consecutiveFailedLoginAttempts;

	public StatisticsResponseAdmDto(Statistics statistics) {
		ratingsGiven = statistics.getRatingsGiven();
		registeredMovies = statistics.getRegisteredMovies();
		consecutiveFailedLoginAttempts = statistics.getConsecutiveFailedLoginAttempts();
	}

}
