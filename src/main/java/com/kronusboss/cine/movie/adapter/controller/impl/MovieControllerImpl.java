package com.kronusboss.cine.movie.adapter.controller.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.kronusboss.cine.adapter.core.controller.dto.UserTokenDto;
import com.kronusboss.cine.movie.adapter.controller.MovieController;
import com.kronusboss.cine.movie.adapter.controller.dto.MovieGenreResponseDto;
import com.kronusboss.cine.movie.adapter.controller.dto.MovieNoteRequestDto;
import com.kronusboss.cine.movie.adapter.controller.dto.MovieNoteResponseDto;
import com.kronusboss.cine.movie.adapter.controller.dto.MovieRequestDto;
import com.kronusboss.cine.movie.adapter.controller.dto.MovieResponseDto;
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
	public Page<MovieResponseDto> listAllMovies(String title, String genre, String sortJoin, Pageable pageable,
			UserTokenDto user) {
		Page<Movie> response = searchMovieUseCase.listAllMovies(title, genre, sortJoin, pageable, user.getId());
		return response.map(MovieResponseDto::new);
	}

	@Override
	public MovieResponseDto getById(UUID id, UserTokenDto user) throws MovieNoteNotFoundException {
		Movie response = searchMovieUseCase.getById(id, user.getId());
		return new MovieResponseDto(response);
	}

	@Override
	public MovieResponseDto save(MovieRequestDto movie, UserTokenDto user) throws DuplicatedMovieException {
		Movie response = createMovieUseCase.save(movie.toDomain(), user.getId());
		return new MovieResponseDto(response);
	}

	@Override
	public MovieResponseDto update(MovieRequestDto movie, UUID movieId, UserTokenDto user)
			throws MovieNotFoundException, UserNotAuthorizedException {
		Movie response = updateMovieUseCase.update(movie.toDomain(), movieId, user.getLogin());
		return new MovieResponseDto(response);
	}

	@Override
	public void delete(UUID id, UserTokenDto user) throws UserNotAuthorizedException {
		deleteMovieUseCase.delete(id, user.getId());
	}

	@Override
	public List<MovieNoteResponseDto> listMovieNotes(UUID movieId, UserTokenDto user) throws MovieNotFoundException {
		return searchMovieNoteUseCase.list(movieId, user.getId())
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

	@Override
	public List<MovieGenreResponseDto> listAllGenresWithMovies() {
		List<MovieGenre> genres = searchMovieGenreUseCase.listAllGenresWithMovies();
		return genres.stream().map(MovieGenreResponseDto::new).collect(Collectors.toList());
	}

}
