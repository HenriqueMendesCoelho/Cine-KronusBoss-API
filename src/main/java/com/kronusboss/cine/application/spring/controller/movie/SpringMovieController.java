package com.kronusboss.cine.application.spring.controller.movie;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kronusboss.cine.adapter.movie.controller.MovieController;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieRequestDto;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieResponseDto;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieException;

@RestController
@RequestMapping("/api/movie")
public class SpringMovieController {
	
	@Autowired
	private MovieController controller;
	
	@GetMapping
	public ResponseEntity<?> getAllMoviesFiltered(@RequestParam String query, Pageable pageable) {
		Page<MovieResponseDto> response = controller.listMoviesByTitle(query, pageable);
		
		if(response.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> getAllMovies(Pageable pageable) {
		Page<MovieResponseDto> response = controller.listMoviesAll(pageable);
		
		if(response.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createMovie(@RequestBody MovieRequestDto request) {
		try {
			MovieResponseDto response = controller.save(request);
			return ResponseEntity.ok(response);
		} catch (DuplicatedMovieException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "code", 400, "message", e.getMessage()));
		}
	}
}
