package com.kronusboss.cine.movie.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.movie.repository.jpa.MovieRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.UpdateMovieUseCase;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;

@Component
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
