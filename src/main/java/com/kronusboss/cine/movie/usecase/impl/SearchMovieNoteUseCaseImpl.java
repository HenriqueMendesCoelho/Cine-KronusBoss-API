package com.kronusboss.cine.movie.usecase.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.movie.repository.jpa.MovieRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.usecase.SearchMovieNoteUseCase;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;

@Component
public class SearchMovieNoteUseCaseImpl implements SearchMovieNoteUseCase {

	@Autowired
	private MovieRepository repository;

	@Override
	public List<MovieNote> list(UUID movieId) throws MovieNotFoundException {

		Movie movie = repository.findById(movieId).orElse(null);

		if (movie == null) {
			throw new MovieNotFoundException();
		}

		return movie.getNotes();
	}

}
