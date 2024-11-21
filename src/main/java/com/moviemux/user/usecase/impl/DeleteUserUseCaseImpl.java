package com.moviemux.user.usecase.impl;

import java.util.List;
import java.util.UUID;

import com.moviemux.user.domain.User;
import com.moviemux.user.usecase.DeleteUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviemux.movie.adapter.repository.MovieRepository;
import com.moviemux.movie.domain.Movie;
import com.moviemux.user.adapter.repository.UserRepository;

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
