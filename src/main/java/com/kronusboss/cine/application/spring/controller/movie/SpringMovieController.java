package com.kronusboss.cine.application.spring.controller.movie;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kronusboss.cine.adapter.core.controller.dto.UserTokenDto;
import com.kronusboss.cine.adapter.movie.controller.MovieController;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieNoteRequestDto;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieNoteResponseDto;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieRequestDto;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieResponseDto;
import com.kronusboss.cine.adapter.util.CredentialUtil;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieException;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieNoteException;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movie")
public class SpringMovieController {

	@Autowired
	private MovieController controller;

	@GetMapping
	public ResponseEntity<?> getAllMoviesFiltered(@RequestParam String query, Pageable pageable) {
		Page<MovieResponseDto> response = controller.listMoviesByTitle(query, pageable);

		if (response.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping("/list")
	public ResponseEntity<?> getAllMovies(Pageable pageable) {
		Page<MovieResponseDto> response = controller.listMoviesAll(pageable);

		if (response.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping("/note/{movieId}")
	public ResponseEntity<?> createMovieNote(@PathVariable @Valid UUID movieId) {
		try {
			List<MovieNoteResponseDto> response = controller.listMoveiNotes(movieId);
			return ResponseEntity.ok(response);
		} catch (MovieNotFoundException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "code", 400, "message", e.getMessage()));
		}
	}

	@PostMapping
	public ResponseEntity<?> createMovie(@RequestBody @Valid MovieRequestDto request) {
		try {
			MovieResponseDto response = controller.save(request);
			return ResponseEntity.ok(response);
		} catch (DuplicatedMovieException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "code", 400, "message", e.getMessage()));
		}
	}

	@PostMapping("/note")
	public ResponseEntity<?> createMovieNote(@RequestBody @Valid MovieNoteRequestDto request,
			@RequestHeader("Authorization") String token) {
		try {
			UserTokenDto user = CredentialUtil.getUserFromToken(token);
			MovieNoteResponseDto response = controller.createMovieNote(request, user);
			return ResponseEntity.ok(response);
		} catch (MovieNotFoundException | DuplicatedMovieNoteException e) {
			return ResponseEntity.badRequest().body(Map.of("error", true, "code", 400, "message", e.getMessage()));
		} catch (JsonProcessingException e) {
			return ResponseEntity.badRequest()
					.body(Map.of("error", true, "code", 500, "message", "Internal Server error"));
		}
	}
}
