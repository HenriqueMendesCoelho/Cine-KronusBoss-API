package com.kronusboss.cine.kronusintegrationtool.application.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kronusboss.cine.kronusintegrationtool.adapter.controller.KronusIntegrationToolController;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.CreditResponseDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.MovieInfoResponseDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.MovieSearchResponseDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.MovieSummaryResponseDto;
import com.kronusboss.cine.kronusintegrationtool.adapter.controller.dto.WatchProvidersResponseDto;

@RestController
@RequestMapping("/api")
public class SpringKronusIntegrationToolController {

	@Autowired
	private KronusIntegrationToolController controller;

	@GetMapping("/movie/tmdb/{movieId}/summary")
	public ResponseEntity<?> summaryMovieTmdb(@PathVariable Long movieId) {
		MovieSummaryResponseDto response = controller.movieSummary(movieId);

		if (response == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(response);

	}

	@GetMapping("/movie/tmdb/{movieId}/info")
	public ResponseEntity<?> infoMovieTmdb(@PathVariable Long movieId) {
		MovieInfoResponseDto response = controller.movieInfo(movieId);

		if (response == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(response);

	}

	@GetMapping("/movie/tmdb")
	public ResponseEntity<?> searchByName(@RequestParam(required = true) String query,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "pt-Br") String language) {
		MovieSearchResponseDto response = controller.movieSearchByName(query, page, language);

		return ResponseEntity.ok(response);

	}

	@GetMapping("/movie/tmdb/popular")
	public ResponseEntity<?> moviesPopular(@RequestParam(required = false, defaultValue = "1") Integer page) {
		MovieSearchResponseDto response = controller.moviesPopular(page);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/movie/tmdb/now-playing")
	public ResponseEntity<?> moviesNowPlaying(@RequestParam(required = false, defaultValue = "1") Integer page) {
		MovieSearchResponseDto response = controller.moviesNowPlaying(page);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/movie/tmdb/top-rated")
	public ResponseEntity<?> moviesTopRated(@RequestParam(required = false, defaultValue = "1") Integer page) {
		MovieSearchResponseDto response = controller.moviesTopRated(page);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/movie/tmdb/{movieId}/recommendations")
	public ResponseEntity<?> moviesRecommendations(@PathVariable Long movieId,
			@RequestParam(required = false, defaultValue = "1") Integer page) {
		MovieSearchResponseDto response = controller.moviesRecommendations(movieId, page);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/movie/tmdb/{movieId}/similar")
	public ResponseEntity<?> moviesSimilar(@PathVariable Long movieId,
			@RequestParam(required = false, defaultValue = "1") Integer page) {
		MovieSearchResponseDto response = controller.moviesSimilar(movieId, page);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/movie/tmdb/discover")
	public ResponseEntity<?> discoverMovies(@RequestParam(required = false) String sort,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false) Integer year, @RequestParam(required = false) String with_genres,
			@RequestParam(required = false) String without_genres) {
		MovieSearchResponseDto response = controller.discoverMovies(sort, page, year, with_genres, without_genres);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/movie/tmdb/{movieId}/watch-providers")
	public ResponseEntity<?> getWatchProviders(@PathVariable Long movieId) {
		WatchProvidersResponseDto response = controller.searchWatchProvidersByMovieId(movieId);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/movie/tmdb/{movieId}/credits")
	public ResponseEntity<?> getCredits(@PathVariable Long movieId) {
		CreditResponseDto response = controller.searchMovieCredits(movieId);

		return ResponseEntity.ok(response);
	}

}
