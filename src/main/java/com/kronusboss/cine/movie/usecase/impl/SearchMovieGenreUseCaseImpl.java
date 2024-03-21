package com.kronusboss.cine.movie.usecase.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.movie.adapter.repository.MovieGenreRepository;
import com.kronusboss.cine.movie.domain.MovieGenre;
import com.kronusboss.cine.movie.usecase.SearchMovieGenreUseCase;

@Component
public class SearchMovieGenreUseCaseImpl implements SearchMovieGenreUseCase {

	@Autowired
	private MovieGenreRepository repository;

	@Override
	public List<MovieGenre> list() {
		return repository.findAllByOrderByNameAsc();
	}

	@Override
	public List<MovieGenre> listAllGenresWithMovies() {
		return repository.findAllGenresHaveMovieAssociated();
	}

}
