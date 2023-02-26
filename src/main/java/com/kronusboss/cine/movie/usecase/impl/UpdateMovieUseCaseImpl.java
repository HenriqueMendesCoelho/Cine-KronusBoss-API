package com.kronusboss.cine.movie.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.movie.repository.jpa.MovieRepository;
import com.kronusboss.cine.adapter.user.repository.jpa.UserRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.UpdateMovieUseCase;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;
import com.kronusboss.cine.user.domain.Role;
import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;

@Component
public class UpdateMovieUseCaseImpl implements UpdateMovieUseCase {

	@Autowired
	private MovieRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Movie update(Movie movie, UUID id, String userEmail)
			throws MovieNotFoundException, UserNotAuthorizedException {

		User user = userRepository.findByEmail(userEmail);
		Movie movieToUpdate = repository.getReferenceById(id);

		if (movieToUpdate == null) {
			throw new MovieNotFoundException();
		}

		if (!user.getRoles().contains(Role.ADM) && movie.getUser().getId() != user.getId()) {
			throw new UserNotAuthorizedException();
		}

		movieToUpdate.setDescription(movie.getDescription());
		movieToUpdate.setDirector(movie.getDirector());
		movieToUpdate.setEnglishUrlTrailer(movie.getEnglishUrlTrailer());
		movieToUpdate.setOriginalTitle(movie.getOriginalTitle());
		movieToUpdate.setPortugueseTitle(movie.getPortugueseTitle());
		movieToUpdate.setPortugueseUrlTrailer(movie.getPortugueseUrlTrailer());
		movieToUpdate.setTmdbId(movie.getTmdbId());
		movieToUpdate.setUrlImage(movie.getUrlImage());
		movieToUpdate.setReleaseDate(movie.getReleaseDate());

		return repository.saveAndFlush(movieToUpdate);
	}

}
