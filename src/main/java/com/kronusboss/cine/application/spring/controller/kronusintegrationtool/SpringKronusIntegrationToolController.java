package com.kronusboss.cine.application.spring.controller.kronusintegrationtool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kronusboss.cine.adapter.kronusintegrationtool.controller.KronusIntegrationToolController;
import com.kronusboss.cine.adapter.kronusintegrationtool.controller.dto.MovieSearchResponseDto;
import com.kronusboss.cine.adapter.kronusintegrationtool.controller.dto.MovieSummaryResponseDto;

@RestController
@RequestMapping("/api")
public class SpringKronusIntegrationToolController {

	@Autowired
	private KronusIntegrationToolController controller;

	@GetMapping("/movie/tmdb/{movieId}/summary")
	public ResponseEntity<?> createMovieNote(@PathVariable Long movieId) {
		MovieSummaryResponseDto response = controller.movieSummary(movieId);

		if (response == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(response);

	}

	@GetMapping("/movie/tmdb")
	public ResponseEntity<?> createMovieNote(@RequestParam(required = true) String query,
			@RequestParam(required = false, defaultValue = "pt-Br") String language) {
		MovieSearchResponseDto response = controller.movieSearchByName(query, language);

		return ResponseEntity.ok(response);

	}

}
