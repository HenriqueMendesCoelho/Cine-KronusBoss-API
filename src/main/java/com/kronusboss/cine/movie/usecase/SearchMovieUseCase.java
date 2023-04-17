package com.kronusboss.cine.movie.usecase;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.exception.MovieNoteNotFoundException;

public interface SearchMovieUseCase {

	Page<Movie> listMoviesAll(Pageable pageable);

	Page<Movie> listAllMoviesOrderByNotesAvg(Pageable pageable);

	Page<Movie> listMoviesByTitle(String title, Pageable pageable);

	Movie getById(UUID id) throws MovieNoteNotFoundException;

}
