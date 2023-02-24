package com.kronusboss.cine.adapter.movie.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kronusboss.cine.adapter.core.controller.dto.UserTokenDto;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieNoteRequestDto;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieNoteResponseDto;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieRequestDto;
import com.kronusboss.cine.adapter.movie.controller.dto.MovieResponseDto;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieException;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieNoteException;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;

public interface MovieController {

	Page<MovieResponseDto> listMoviesAll(Pageable pageable);

	Page<MovieResponseDto> listMoviesByTitle(String title, Pageable pageable);

	MovieResponseDto getById(UUID id);

	MovieResponseDto save(MovieRequestDto movie) throws DuplicatedMovieException;

	MovieResponseDto update(MovieRequestDto movie, UUID id) throws MovieNotFoundException;

	void delete(UUID id);

	List<MovieNoteResponseDto> listMoveiNotes(UUID movieId) throws MovieNotFoundException;

	MovieNoteResponseDto createMovieNote(MovieNoteRequestDto request, UserTokenDto user)
			throws MovieNotFoundException, DuplicatedMovieNoteException;

}
