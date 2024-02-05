package com.kronusboss.cine.statistic.usecase.impl;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kronusboss.cine.movie.adapter.repository.MovieGenreRepository;
import com.kronusboss.cine.movie.adapter.repository.MovieNoteRepository;
import com.kronusboss.cine.movie.adapter.repository.MovieRepository;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.domain.MovieGenre;
import com.kronusboss.cine.movie.domain.MovieNote;
import com.kronusboss.cine.statistic.domain.MovieStatistic;
import com.kronusboss.cine.statistic.usecase.MovieStatisticsUseCase;

@Component
public class MovieStatisticsUseCaseImpl implements MovieStatisticsUseCase {

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
	private static final int NUMBER_OF_MONTHS = 6;

	@Autowired
	private MovieRepository repository;

	@Autowired
	private MovieGenreRepository genreRepository;

	@Autowired
	private MovieNoteRepository movieNoteRepository;

	@Override
	public MovieStatistic getStatistics() {
		List<Movie> movies = repository.findAll();
		return MovieStatistic.builder()
				.totalNumberOfMovies(movies.size())
				.averageRate(averageRate())
				.averageRuntime(averageRuntime(movies))
				.moviesSixMonthsAgo(moviesSixMonthsAgo(movies))
				.moviesByGender(movieByGender())
				.build();
	}

	private Map<String, Long> moviesSixMonthsAgo(List<Movie> movies) {
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime sixMonthsAgo = today.minusMonths(NUMBER_OF_MONTHS).withDayOfMonth(1);

		Map<YearMonth, Long> groupedMovies = movies.stream()
				.filter(obj -> obj.getCreatedAt().toLocalDateTime().isAfter(sixMonthsAgo))
				.collect(Collectors.groupingBy(obj -> YearMonth.from(obj.getCreatedAt()), Collectors.counting()));

		for (int i = 0; i < NUMBER_OF_MONTHS; i++) {
			YearMonth monthToCheck = YearMonth.from(today.minusMonths(i).withDayOfMonth(1));
			groupedMovies.putIfAbsent(monthToCheck, 0L);
		}

		Map<String, Long> result = groupedMovies.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(e -> getMonthNameInPortuguese(e.getKey().getMonthValue()),
						Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		return result;
	}

	private Map<String, Integer> movieByGender() {
		List<MovieGenre> genres = genreRepository.findAll();

		Map<String, Integer> result = genres.stream()
				.collect(Collectors.groupingBy(MovieGenre::getName,
						Collectors.mapping(g -> g.getMovies().size(), Collectors.summingInt(Integer::intValue))));

		result = result.entrySet()
				.stream()
				.filter(entry -> entry.getValue() != 0)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		return result;
	}

	private String getMonthNameInPortuguese(int monthValue) {
		String[] monthNames = new DateFormatSymbols(Locale.forLanguageTag("pt-BR")).getMonths();
		return monthNames[monthValue - 1];
	}

	private Double averageRate() {
		List<MovieNote> movieNotes = movieNoteRepository.findAll();
		List<Integer> notes = movieNotes.stream().map(MovieNote::getNote).toList();

		return Double.valueOf(DECIMAL_FORMAT.format(notes.stream().mapToDouble(n -> n).average().orElse(0)));
	}

	private Integer averageRuntime(List<Movie> movies) {
		return (int) movies.stream().mapToDouble(m -> m.getRuntime()).average().orElse(0);
	}

}
