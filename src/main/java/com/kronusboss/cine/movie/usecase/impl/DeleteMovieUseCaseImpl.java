package com.kronusboss.cine.movie.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.movie.repository.jpa.MovieRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.DeleteMovieUseCase;

@Component
public class DeleteMovieUseCaseImpl implements DeleteMovieUseCase {

	@Autowired
	private MovieRepository repository;

	@Override
	public void delete(UUID id) {
		Movie movie = repository.findById(id).get();

		if (movie == null) {
			return;
		}

		repository.delete(movie);
	}

}
