package com.kronusboss.cine.adapter.movie.controller.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.kronusboss.cine.adapter.core.controller.dto.UserTokenDto;
import com.kronusboss.cine.adapter.movie.controller.MovieController;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieGenreResponseDto;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieNoteRequestDto;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieNoteResponseDto;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieRequestDto;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieResponseDto;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.domain.MovieGenre;
import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.usecase.CreateMovieNoteUseCase;
import com.kronusboss.cine.movie.usecase.CreateMovieUseCase;
import com.kronusboss.cine.movie.usecase.DeleteMovieNoteUseCase;
import com.kronusboss.cine.movie.usecase.DeleteMovieUseCase;
import com.kronusboss.cine.movie.usecase.SearchMovieGenreUseCase;
import com.kronusboss.cine.movie.usecase.SearchMovieNoteUseCase;
import com.kronusboss.cine.movie.usecase.SearchMovieUseCase;
import com.kronusboss.cine.movie.usecase.UpdateMovieNoteUseCase;
import com.kronusboss.cine.movie.usecase.UpdateMovieUseCase;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieException;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieNoteException;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;
import com.kronusboss.cine.movie.usecase.exception.MovieNoteNotFoundException;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;

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

	@Autowired
	private CreateMovieNoteUseCase createMovieNoteUseCase;

	@Autowired
	private SearchMovieNoteUseCase searchMovieNoteUseCase;

	@Autowired
	private UpdateMovieNoteUseCase updateMovieNoteUseCase;

	@Autowired
	private DeleteMovieNoteUseCase deleteMovieNoteUseCase;

	@Autowired
	private SearchMovieGenreUseCase searchMovieGenreUseCase;

	@Override
	public Page<MovieResponseDto> listMoviesAll(Pageable pageable) {
		Page<Movie> response = searchMovieUseCase.listMoviesAll(pageable);
		return response.map(MovieResponseDto::new);
	}

	@Override
	public Page<MovieResponseDto> listMoviesByTitle(String title, Pageable pageable) {
		Page<Movie> response = searchMovieUseCase.listMoviesByTitle(title, pageable);
		return response.map(MovieResponseDto::new);
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
	public MovieResponseDto update(MovieRequestDto movie, UUID id, UserTokenDto user)
			throws MovieNotFoundException, UserNotAuthorizedException {
		Movie response = updateMovieUseCase.update(movie.toDomain(), id, user.getLogin());
		return new MovieResponseDto(response);
	}

	@Override
	public void delete(UUID id, UserTokenDto user) throws UserNotAuthorizedException {
		deleteMovieUseCase.delete(id, user.getId());
	}

	@Override
	public List<MovieNoteResponseDto> listMoveiNotes(UUID movieId) throws MovieNotFoundException {
		return searchMovieNoteUseCase.list(movieId)
				.stream()
				.map(MovieNoteResponseDto::new)
				.collect(Collectors.toList());
	}

	@Override
	public MovieNoteResponseDto createMovieNote(MovieNoteRequestDto request, UserTokenDto user)
			throws MovieNotFoundException, DuplicatedMovieNoteException {
		MovieNote response = createMovieNoteUseCase.create(request.getMovieId(), request.getNote(), user.getLogin());
		return new MovieNoteResponseDto(response);
	}

	@Override
	public MovieNoteResponseDto updateMovieNote(UUID movieId, MovieNoteRequestDto request, UserTokenDto user)
			throws MovieNoteNotFoundException {
		MovieNote response = updateMovieNoteUseCase.update(user.getId(), movieId, request.getNote());
		return new MovieNoteResponseDto(response);
	}

	@Override
	public void deleteMovieNote(UUID movieId, UserTokenDto user) {
		deleteMovieNoteUseCase.delete(user.getId(), movieId);
	}

	@Override
	public List<MovieGenreResponseDto> listGenres() {
		List<MovieGenre> genres = searchMovieGenreUseCase.list();
		return genres.stream().map(MovieGenreResponseDto::new).collect(Collectors.toList());
	}

}
