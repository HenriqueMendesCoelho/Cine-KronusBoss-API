package com.kronusboss.cine.user.usecase.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kronusboss.cine.movie.adapter.repository.jpa.MovieRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.user.adapter.repository.jpa.UserRepository;
import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.user.usecase.DeleteUserUseCase;

@Service
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

	@Autowired
	UserRepository repository;

	@Autowired
	MovieRepository movieRepository;

	@Override
	public void deleteUser(UUID id) {

		User userToDelete = repository.getReferenceById(id);

		if (userToDelete == null) {
			return;
		}

		List<Movie> movies = userToDelete.getMovies();

		for (Movie obj : movies) {
			obj.setUser(null);
		}
		movieRepository.saveAllAndFlush(movies);

		userToDelete.setMovies(null);
		repository.save(userToDelete);

		repository.delete(userToDelete);

	}

}
