package com.kronusboss.cine.movie.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.movie.adapter.repository.jpa.MovieNoteRepository;
import com.kronusboss.cine.movie.adapter.repository.jpa.MovieRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.domain.MovieNoteKey;
import com.kronusboss.cine.movie.usecase.CreateMovieNoteUseCase;
import com.kronusboss.cine.movie.usecase.exception.DuplicatedMovieNoteException;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;
import com.kronusboss.cine.user.adapter.repository.jpa.UserRepository;
import com.kronusboss.cine.user.domain.User;

@Component
public class CreateMovieNoteUseCaseImpl implements CreateMovieNoteUseCase {

	@Autowired
	private MovieNoteRepository repository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public MovieNote create(UUID movieId, Integer note, String emailUserLoged)
			throws MovieNotFoundException, DuplicatedMovieNoteException {
		User user = userRepository.findByEmail(emailUserLoged);
		Movie movie = movieRepository.findById(movieId).orElse(null);

		if (movie == null) {
			throw new MovieNotFoundException();
		}

		MovieNote movieNoteExists = repository.findById(new MovieNoteKey(user.getId(), movie.getId())).orElse(null);

		if (movieNoteExists != null) {
			throw new DuplicatedMovieNoteException();
		}

		MovieNote movieNote = MovieNote.builder().movie(movie).note(note).user(user).build();

		userRepository.saveAndFlush(user);

		return repository.saveAndFlush(movieNote);
	}

}
