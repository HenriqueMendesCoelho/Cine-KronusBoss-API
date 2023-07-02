package com.kronusboss.cine.user.adapter.controller.dto;

import com.kronusboss.cine.user.domain.Statistics;

import lombok.Data;

@Data
public class StatisticsResponseDto {

	private Integer ratingsGiven;
	private Integer registeredMovies;
	private Integer displayTime;
	private Double averageRatingMovies;

	public StatisticsResponseDto(Statistics statistics) {
		ratingsGiven = statistics.getRatingsGiven();
		registeredMovies = statistics.getRegisteredMovies();
		displayTime = statistics.getDisplayTime();
		averageRatingMovies = statistics.getAverageRatingMovies();
	}

}
