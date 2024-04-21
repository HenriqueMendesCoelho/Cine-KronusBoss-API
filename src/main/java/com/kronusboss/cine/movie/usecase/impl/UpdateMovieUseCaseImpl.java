package com.kronusboss.cine.movie.usecase.impl;

import com.kronusboss.cine.discord.usecase.UpdateMessageWebhookUseCase;
import com.kronusboss.cine.movie.adapter.repository.MovieRepository;
import com.kronusboss.cine.movie.adapter.repository.rest.MovieSocketRespository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.UpdateMovieUseCase;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;
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
public class UpdateMovieUseCaseImpl implements UpdateMovieUseCase {

	@Autowired
	private MovieRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UpdateMessageWebhookUseCase updateMessageWebhookUseCase;

	@Autowired
	private MovieSocketRespository movieSocketRespository;

	@Override
	@CacheEvict(value = "statistics", allEntries = true)
	public Movie update(Movie movie, UUID id, String userEmail)
			throws MovieNotFoundException, UserNotAuthorizedException {
		User user = userRepository.findByEmail(userEmail);
		Movie movieToUpdate = repository.findById(id).orElse(null);

		if (movieToUpdate == null) {
			throw new MovieNotFoundException();
		}

		if (movieToUpdate.getUser() == null && !user.getRoles().contains(Role.ADM)) {
			throw new UserNotAuthorizedException();
		}

		if (!user.getRoles().contains(Role.ADM) && movieToUpdate.getUser().getId() != user.getId()) {
			throw new UserNotAuthorizedException();
		}

		final boolean emitEvent = movie.isShowNotes() != movieToUpdate.isShowNotes();

		movieToUpdate.setDescription(movie.getDescription());
		movieToUpdate.setDirector(movie.getDirector());
		movieToUpdate.setEnglishUrlTrailer(movie.getEnglishUrlTrailer());
		movieToUpdate.setOriginalTitle(movie.getOriginalTitle());
		movieToUpdate.setPortugueseTitle(movie.getPortugueseTitle());
		movieToUpdate.setPortugueseUrlTrailer(movie.getPortugueseUrlTrailer());
		movieToUpdate.setTmdbId(movie.getTmdbId());
		movieToUpdate.setUrlImage(movie.getUrlImage());
		movieToUpdate.setReleaseDate(movie.getReleaseDate());
		movieToUpdate.setImdbId(movie.getImdbId());
		movieToUpdate.setGenres(movie.getGenres());
		movieToUpdate.setRuntime(movie.getRuntime());
		movieToUpdate.setShowNotes(movie.isShowNotes());

		Movie movieUpdated = repository.saveAndFlush(movieToUpdate);
		updateMessageWebhookUseCase.updateMovieMessage(movieToUpdate.getId());
		if (emitEvent) {
			sendEventSocket(movieUpdated);
		}

		return movieUpdated;
	}

	private void sendEventSocket(Movie movie) {
		try {
			movieSocketRespository.emitEventMovie(movie.getId(), "update-movie", null);
		} catch (Exception e) {
			log.error("Error to emit event on movie update (UpdateMovieUseCaseImpl): ", e);
		}

	}
}
