package com.kronusboss.cine.movie.usecase.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.discord.usecase.UpdateMessageWebhookUseCase;
import com.kronusboss.cine.movie.adapter.repository.jpa.MovieRepository;
import com.kronusboss.cine.movie.adapter.repository.rest.MovieSocketRespository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.usecase.UpdateMovieUseCase;
import com.kronusboss.cine.movie.usecase.exception.MovieNotFoundException;
import com.kronusboss.cine.user.adapter.repository.jpa.UserRepository;
import com.kronusboss.cine.user.domain.Role;
import com.kronusboss.cine.user.domain.User;
import com.kronusboss.cine.user.usecase.exception.UserNotAuthorizedException;

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
	public Movie update(Movie movie, UUID id, String userEmail)
			throws MovieNotFoundException, UserNotAuthorizedException {

		User user = userRepository.findByEmail(userEmail);
		Movie movieToUpdate = repository.getReferenceById(id);

		if (movieToUpdate == null) {
			throw new MovieNotFoundException();
		}

		if (movieToUpdate.getUser() == null && !user.getRoles().contains(Role.ADM)) {
			throw new UserNotAuthorizedException();
		}

		if (!user.getRoles().contains(Role.ADM) && movieToUpdate.getUser().getId() != user.getId()) {
			throw new UserNotAuthorizedException();
		}

		boolean emitUpdateEventSocket = movie.isShowNotes() != movieToUpdate.isShowNotes() ? true : false;

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

		if (emitUpdateEventSocket) {
			movieSocketRespository.emitEventMovie(movieUpdated.getId(), "update-movie", null);
		}

		return movieUpdated;
	}

}
