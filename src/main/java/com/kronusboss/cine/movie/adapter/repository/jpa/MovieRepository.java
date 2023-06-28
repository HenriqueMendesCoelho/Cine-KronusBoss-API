package com.kronusboss.cine.movie.adapter.repository.jpa;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.kronusboss.cine.movie.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, UUID> {

	@Transactional(readOnly = true)
	Movie findByTmdbId(Long tmdbId);

	Page<Movie> findAll(Pageable pageable);

	@Query(value = "select * from movie where portuguese_title ilike any(:title) or english_title ilike any(:title) or original_title ilike any(:title)", nativeQuery = true)
	Page<Movie> findMovieByTitleIlikeAny(@Param("title") String[] title, Pageable pageable);

	@Query(value = "select * from movie where portuguese_title ilike all(:title) or english_title ilike all(:title) or original_title ilike all(:title)", nativeQuery = true)
	Page<Movie> findMovieByTitleIlikeAll(@Param("title") String[] title, Pageable pageable);

	@Query(value = "SELECT m.*, COALESCE(AVG(n.note), 0) AS average_notes FROM movie m "
			+ " LEFT JOIN movie_note n ON m.id = n.movie_id GROUP BY m.id "
			+ " ORDER BY average_notes ASC, m.portuguese_title ASC;", nativeQuery = true)
	Page<Movie> findMovieOrderByNoteAvgASC(Pageable pageable);

	@Query(value = "SELECT m.*, COALESCE(AVG(n.note), 0) AS average_notes FROM movie m "
			+ " LEFT JOIN movie_note n ON m.id = n.movie_id GROUP BY m.id "
			+ " ORDER BY average_notes DESC, m.portuguese_title ASC;", nativeQuery = true)
	Page<Movie> findMovieOrderByNoteAvgDESC(Pageable pageable);
}
