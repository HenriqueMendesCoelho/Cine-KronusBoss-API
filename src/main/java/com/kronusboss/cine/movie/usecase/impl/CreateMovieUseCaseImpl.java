package com.kronusboss.cine.movie.usecase.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.movie.repository.jpa.MovieRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.CreateMovieUseCase;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieException;

@Component
public class CreateMovieUseCaseImpl implements CreateMovieUseCase {

	@Autowired
	private MovieRepository repository;

	@Override
	public Movie save(Movie movie) throws DuplicatedMovieException {

		if (repository.findByTmdbId(movie.getTmdbId()) != null) {
			throw new DuplicatedMovieException("This tmdb id is already registered");
		}

		return repository.saveAndFlush(movie);
	}

}
