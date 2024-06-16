package com.kronusboss.cine.kronusintegrationtool.application.spring.controller;

import com.kronusboss.cine.kronusintegrationtool.adapter.controller.KronusIntegrationToolController;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.MovieInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public/movie/tmdb")
@RequiredArgsConstructor
public class KronusIntegrationToolPublicSpringController {

	private final KronusIntegrationToolController controller;

	@GetMapping("/{movieId}/info")
	public ResponseEntity<?> infoMovieTmdb(@PathVariable Long movieId) {
		MovieInfoResponseDto response = controller.movieInfo(movieId);

		if (response == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(response);

	}

}
