package com.kronusboss.cine.adapter.controller.user.dto;

import com.kronusboss.cine.domain.user.Statistics;

import lombok.Data;

@Data
public class StatisticsResponseDto {
	
	private int ratingsGiven;
	
	private int registeredMovies;
	
	public StatisticsResponseDto(Statistics statistics) {
		ratingsGiven = statistics.getRatingsGiven();
		registeredMovies = statistics.getRegisteredMovies();
	}

}
