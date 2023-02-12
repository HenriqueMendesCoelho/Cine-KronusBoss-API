package com.kronusboss.cine.movie.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.movie.repository.jpa.MovieRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.SearchMovieUseCase;

@Component
public class SearchMovieUseCaseImpl implements SearchMovieUseCase {
	
	@Autowired
	private MovieRepository repository;

	@Override
	public Page<Movie> listMoviesAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Page<Movie> listMoviesByTitle(String title, Pageable pageable) {
		return repository.findMovieByTitleCustom(title, pageable);
	}

	@Override
	public Movie getById(UUID id) {
		Movie movie = repository.findById(id).get();
		
		if(movie == null) {
			return null;
		}
		
		return movie;
	}

}
