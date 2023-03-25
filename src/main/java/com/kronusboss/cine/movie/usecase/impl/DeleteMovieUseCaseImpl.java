package com.kronusboss.cine.movie.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.adapter.movie.repository.jpa.MovieRepository;
import com.kronusboss.cine.adapter.user.repository.jpa.UserRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.DeleteMovieUseCase;
import com.kronusboss.cine.user.domain.Role;
import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;

@Component
public class DeleteMovieUseCaseImpl implements DeleteMovieUseCase {

	@Autowired
	private MovieRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void delete(UUID id, UUID idUserLoged) throws UserNotAuthorizedException {
		Movie movie = repository.findById(id).get();

		if (movie == null) {
			return;
		}

		User user = userRepository.findById(idUserLoged).orElse(null);

		if (user == null) {
			return;
		}

		if (movie.getUser() == null && !user.getRoles().contains(Role.ADM)) {
			throw new UserNotAuthorizedException("user can not delete this movie");
		}

		if (!user.getRoles().contains(Role.ADM) && movie.getUser().getId() != user.getId()) {
			throw new UserNotAuthorizedException("user can not delete this movie");
		}

		repository.delete(movie);
	}

}
