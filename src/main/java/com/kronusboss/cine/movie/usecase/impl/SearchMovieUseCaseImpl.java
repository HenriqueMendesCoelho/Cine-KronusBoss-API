package com.kronusboss.cine.movie.usecase.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.movie.adapter.repository.jpa.MovieRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.movie.usecase.SearchMovieUseCase;
import com.kronusboss.cine.movie.usecase.exception.MovieNoteNotFoundException;

@Component
public class SearchMovieUseCaseImpl implements SearchMovieUseCase {

	@Autowired
	private MovieRepository repository;

	@Override
	public Page<Movie> listMoviesAll(Pageable pageable, UUID userId) {
		Page<Movie> movies = repository.findAll(pageable);

		return omitNoteIfNeeded(movies, userId);
	}

	@Override
	public Page<Movie> listAllMoviesOrderByNotesAvg(String sortJoin, Pageable pageable, UUID userId) {
		Pageable objPageableWithoutSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

		if (sortJoin.contains("notes,asc")) {
			return omitNoteIfNeeded(repository.findMovieOrderByNoteAvgASC(objPageableWithoutSort), userId);
		}

		return omitNoteIfNeeded(repository.findMovieOrderByNoteAvgDESC(objPageableWithoutSort), userId);
	}

	@Override
	public Page<Movie> listMoviesByTitle(String title, Pageable pageable, UUID userId) {
		String[] titleSplitBySpace = title.split(" ");

		String[] params = new String[titleSplitBySpace.length];
		for (int i = 0; i < params.length; i++) {
			params[i] = "%" + titleSplitBySpace[i] + "%";
		}
		Page<Movie> movies = repository.findMovieByTitleIlikeAll(params, pageable);

		return omitNoteIfNeeded(movies, userId);
	}

	@Override
	public Movie getById(UUID id, UUID userId) throws MovieNoteNotFoundException {
		Movie movie = repository.findById(id).orElse(null);

		if (movie == null) {
			throw new MovieNoteNotFoundException();
		}

		Duration duration = Duration.between(movie.getCreatedAt(), LocalDateTime.now());
		if (duration.getSeconds() >= 1800 && !movie.isShowNotes()) {
			movie.setShowNotes(true);
			repository.saveAndFlush(movie);
		}

		Movie result = omitNoteIfNeeded(movie, userId);
		result.getNotes().sort(MovieNote.comparator());

		return result;
	}

	private Page<Movie> omitNoteIfNeeded(Page<Movie> movies, UUID userId) {
		return movies.map(m -> {
			if (!m.isShowNotes() && CollectionUtils.isNotEmpty(m.getNotes())) {
				List<MovieNote> notes = m.getNotes().stream().map(n -> {
					if (!n.getUser().getId().equals(userId)) {
						n.setNote(null);
					}

					return n;
				}).collect(Collectors.toList());
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
			}).collect(Collectors.toList());
			movie.setNotes(notes);
		}

		return movie;
	}

}
