package com.kronusboss.cine.adapter.movie.controller.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.kronusboss.cine.adapter.movie.controller.MovieController;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieRequestDto;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieResponseDto;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.CreateMovieUseCase;
import com.kronusboss.cine.movie.usecase.DeleteMovieUseCase;
import com.kronusboss.cine.movie.usecase.SearchMovieUseCase;
import com.kronusboss.cine.movie.usecase.UpdateMovieUseCase;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieException;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;

@Controller
public class MovieControllerImpl implements MovieController {
	
	@Autowired
	private CreateMovieUseCase createMovieUseCase;
	
	@Autowired
	private SearchMovieUseCase searchMovieUseCase;
	
	@Autowired
	private UpdateMovieUseCase updateMovieUseCase;
	
	@Autowired
	private DeleteMovieUseCase deleteMovieUseCase;

	@Override
	public Page<MovieResponseDto> listMoviesAll(Pageable pageable) {
		Page<Movie> response = searchMovieUseCase.listMoviesAll(pageable);
		return response.map(m -> new MovieResponseDto(m));
	}

	@Override
	public Page<MovieResponseDto> listMoviesByTitle(String title, Pageable pageable) {
		Page<Movie> response = searchMovieUseCase.listMoviesByTitle(title, pageable);
		return response.map(m -> new MovieResponseDto(m));
	}

	@Override
	public MovieResponseDto getById(UUID id) {
		Movie response = searchMovieUseCase.getById(id);
		return new MovieResponseDto(response);
	}

	@Override
	public MovieResponseDto save(MovieRequestDto movie) throws DuplicatedMovieException {
		Movie response = createMovieUseCase.save(movie.toDomain());
		return new MovieResponseDto(response);
	}

	@Override
	public MovieResponseDto update(MovieRequestDto movie, UUID id) throws MovieNotFoundException {
		Movie response = updateMovieUseCase.update(movie.toDomain(), id);
		return new MovieResponseDto(response);
	}

	@Override
	public void delete(UUID id) {
		deleteMovieUseCase.delete(id);
	}

}
