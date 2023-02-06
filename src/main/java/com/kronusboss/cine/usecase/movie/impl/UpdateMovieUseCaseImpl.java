package com.kronusboss.cine.usecase.movie.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.kronusboss.cine.adapter.repository.jpa.movie.MovieRepository;
import com.kronusboss.cine.domain.movie.Movie;
import com.kronusboss.cine.usecase.movie.UpdateMovieUseCase;
import com.kronusboss.cine.usecase.movie.exception.MovieNotFoundException;

public class UpdateMovieUseCaseImpl implements UpdateMovieUseCase {
	
	@Autowired
	private MovieRepository repository;
	
	@Override
	public Movie update(Movie movie, UUID id) throws MovieNotFoundException {
		
		Movie movieToUpdate = repository.getReferenceById(id);
		
		if(movieToUpdate == null) {
			throw new MovieNotFoundException();
		}
		
		movieToUpdate.setDescription(movie.getDescription());
		movieToUpdate.setDirector(movie.getDirector());
		movieToUpdate.setEnglishUrlTrailer(movie.getEnglishUrlTrailer());
		movieToUpdate.setOriginalTitle(movie.getOriginalTitle());
		movieToUpdate.setPortugueseTitle(movie.getPortugueseTitle());
		movieToUpdate.setPortugueseUrlTrailer(movie.getPortugueseUrlTrailer());
		movieToUpdate.setTmdbId(movie.getTmdbId());
		movieToUpdate.setUrlImage(movie.getUrlImage());
		movieToUpdate.setYear(movie.getYear());
		
		return repository.saveAndFlush(movieToUpdate);
	}

}
