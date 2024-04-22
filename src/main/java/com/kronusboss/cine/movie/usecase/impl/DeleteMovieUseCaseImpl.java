package com.kronusboss.cine.movie.usecase.impl;

import com.kronusboss.cine.movie.adapter.repository.MovieRepository;
import com.kronusboss.cine.movie.adapter.repository.rest.MovieSocketRespository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.DeleteMovieUseCase;
import com.kronusboss.cine.user.adapter.repository.UserRepository;
import com.kronusboss.cine.user.domain.Role;
import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class DeleteMovieUseCaseImpl implements DeleteMovieUseCase {

	@Autowired
	private MovieRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MovieSocketRespository socketRepository;

	@Override
	@CacheEvict(value = "statistics", allEntries = true)
	public void delete(UUID id, UUID idUserLoged) throws UserNotAuthorizedException {
		Movie movie = repository.findById(id).orElse(null);

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
		emitEventSocket(id, idUserLoged);

	}

	private void emitEventSocket(UUID movieId, UUID idUserLoged) {
		try {
			socketRepository.emitAllMoviesEvent(null);
			socketRepository.emitEventMovie(movieId, "deleted-movie", null, idUserLoged.toString());
		} catch (Exception e) {
			log.error("Error to emit event on movie creation raised: ", e);
		}
	}

}
