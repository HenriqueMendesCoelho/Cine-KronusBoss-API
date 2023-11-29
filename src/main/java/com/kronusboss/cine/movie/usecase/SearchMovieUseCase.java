package com.kronusboss.cine.movie.usecase;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.exception.MovieNoteNotFoundException;

public interface SearchMovieUseCase {

	Page<Movie> listAllMovies(String title, String genre, String sortJoin, Pageable pageable, UUID userId);

	Movie getById(UUID id, UUID userId) throws MovieNoteNotFoundException;

}
