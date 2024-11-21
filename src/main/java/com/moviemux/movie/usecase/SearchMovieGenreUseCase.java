package com.moviemux.movie.usecase;

import java.util.List;

import com.moviemux.movie.domain.MovieGenre;

public interface SearchMovieGenreUseCase {

	List<MovieGenre> list();

	List<MovieGenre> listAllGenresWithMovies();
}
