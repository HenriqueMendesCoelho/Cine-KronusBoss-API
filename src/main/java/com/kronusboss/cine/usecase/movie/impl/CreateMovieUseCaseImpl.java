package com.kronusboss.cine.usecase.movie.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kronusboss.cine.adapter.repository.jpa.movie.MovieRepository;
import com.kronusboss.cine.domain.movie.Movie;
import com.kronusboss.cine.usecase.movie.CreateMovieUseCase;

public class CreateMovieUseCaseImpl implements CreateMovieUseCase {
	
	@Autowired
	private MovieRepository repository;

	@Override
	public Movie save(Movie movie) {
		return repository.saveAndFlush(movie);
	}

}
