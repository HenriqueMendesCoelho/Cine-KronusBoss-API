package com.kronusboss.cine.usecase.movie.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.kronusboss.cine.adapter.repository.jpa.movie.MovieRepository;
import com.kronusboss.cine.domain.movie.Movie;
import com.kronusboss.cine.usecase.movie.DeleteMovieUseCase;

public class DeleteMovieUseCaseImpl implements DeleteMovieUseCase {
	
	@Autowired
	private MovieRepository repository;
	
	@Override
	public void delete(UUID id) {
		Movie movie = repository.findById(id).get();
		
		if(movie == null) {
			return;
		}
		
		repository.delete(movie);
	}

}
