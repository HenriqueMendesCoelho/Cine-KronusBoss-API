package com.kronusboss.cine.statistic.application.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kronusboss.cine.statistic.adapter.controller.StatisticController;
import com.kronusboss.cine.statistic.adapter.controller.dto.MovieStatisticResponseDto;

@RestController
@RequestMapping("/api/statistic")
public class SpringStatisticController {

	@Autowired
	private StatisticController controller;

	@GetMapping("/movies")
	public ResponseEntity<?> sendMovieMessageDiscord() {
		MovieStatisticResponseDto response = controller.charts();

		return ResponseEntity.ok(response);
	}
}
