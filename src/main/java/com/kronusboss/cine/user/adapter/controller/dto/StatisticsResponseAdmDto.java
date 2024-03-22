package com.kronusboss.cine.user.adapter.controller.dto;

import com.kronusboss.cine.user.domain.Statistics;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class StatisticsResponseAdmDto {

	private Integer ratingsGiven;
	private Integer registeredMovies;
	private Integer consecutiveFailedLoginAttempts;
	private Integer displayTime;
	private Double averageRatingMovies;
	private OffsetDateTime lastLoginAt;

	public StatisticsResponseAdmDto(Statistics statistics) {
		ratingsGiven = statistics.getRatingsGiven();
		registeredMovies = statistics.getRegisteredMovies();
		consecutiveFailedLoginAttempts = statistics.getConsecutiveFailedLoginAttempts();
		displayTime = statistics.getDisplayTime();
		averageRatingMovies = statistics.getAverageRatingMovies();
		lastLoginAt = statistics.getLastLoginAt();
	}

}
