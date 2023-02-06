package com.kronusboss.cine.usecase.movie.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kronusboss.cine.adapter.repository.jpa.movie.MovieRepository;
import com.kronusboss.cine.domain.movie.Movie;
import com.kronusboss.cine.usecase.movie.CreateMovieUseCase;
import com.kronusboss.cine.usecase.movie.exception.DuplicatedMovieException;

public class CreateMovieUseCaseImpl implements CreateMovieUseCase {
	
	@Autowired
	private MovieRepository repository;

	@Override
	public Movie save(Movie movie) throws DuplicatedMovieException {
		
		if(repository.findByTmdbId(movie.getTmdbId()) != null) {
			throw new DuplicatedMovieException("This tmdb id is already registered");
		}
		
		return repository.saveAndFlush(movie);
	}

}
