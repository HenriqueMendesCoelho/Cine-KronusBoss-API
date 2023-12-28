package com.kronusboss.cine.movie.usecase.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.movie.adapter.repository.MovieRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.usecase.SearchMovieUseCase;
import com.kronusboss.cine.movie.usecase.exception.MovieNoteNotFoundException;

@Component
public class SearchMovieUseCaseImpl implements SearchMovieUseCase {

	@Autowired
	private MovieRepository repository;

	@Override
	public Movie getById(UUID id, UUID userId) throws MovieNoteNotFoundException {
		Movie movie = repository.findById(id).orElse(null);

		if (movie == null) {
			throw new MovieNoteNotFoundException();
		}

		Duration duration = Duration.between(movie.getCreatedAt().toLocalDateTime(), LocalDateTime.now());
		if (duration.getSeconds() >= 1800 && !movie.isShowNotes()) {
			movie.setShowNotes(true);
			repository.saveAndFlush(movie);
		}

		Movie result = omitNoteIfNeeded(movie, userId);
		result.getNotes().sort(MovieNote.comparator());

		return result;
	}

	@Override
	public Page<Movie> listAllMovies(String title, String genre, String sortJoin, Pageable pageable, UUID userId) {
		List<String> titles = null;
		if (StringUtils.isNotBlank(title)) {
			titles = Arrays.asList(title.split(" "));
		}
		List<Long> genres = null;
		if (StringUtils.isNotBlank(genre)) {
			genres = Arrays.asList(genre.split(",")).stream().map(Long::parseLong).toList();
		}
		Page<Movie> movies = repository.findMovieFilteredCustom(titles, genres, sortJoin, pageable);

		return omitNoteIfNeeded(movies, userId);
	}

	private Page<Movie> omitNoteIfNeeded(Page<Movie> movies, UUID userId) {
		return movies.map(m -> {
			if (!m.isShowNotes() && CollectionUtils.isNotEmpty(m.getNotes())) {
				List<MovieNote> notes = m.getNotes().stream().map(n -> {
					if (!n.getUser().getId().equals(userId)) {
						n.setNote(null);
					}

					return n;
				}).toList();
				m.setNotes(notes);
			}

			return m;
		});
	}

	private Movie omitNoteIfNeeded(Movie movie, UUID userId) {
		if (!movie.isShowNotes() && CollectionUtils.isNotEmpty(movie.getNotes())) {
			List<MovieNote> notes = movie.getNotes().stream().map(n -> {
				if (!n.getUser().getId().equals(userId)) {
					n.setNote(null);
				}

				return n;
			}).toList();
			movie.setNotes(notes);
		}

		return movie;
	}

}
