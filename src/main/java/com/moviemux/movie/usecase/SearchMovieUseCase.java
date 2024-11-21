package com.moviemux.movie.usecase;

import java.util.UUID;

import com.moviemux.movie.usecase.exception.MovieNoteNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.moviemux.movie.domain.Movie;

public interface SearchMovieUseCase {

	Page<Movie> listAllMovies(String title, String genre, String sortJoin, Pageable pageable, UUID userId);

	Movie getById(UUID id, UUID userId) throws MovieNoteNotFoundException;

}
