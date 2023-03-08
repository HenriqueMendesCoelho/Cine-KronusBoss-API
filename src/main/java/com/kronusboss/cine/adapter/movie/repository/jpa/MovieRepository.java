package com.kronusboss.cine.adapter.movie.repository.jpa;

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

	@Query(value = "select * from movie where portuguese_title ILIKE CONCAT('%', :title, '%')"
			+ " or original_title ILIKE CONCAT('%', :title, '%') or english_title ILIKE CONCAT('%', :title, '%')", nativeQuery = true)
	Page<Movie> findMovieByTitleCustom(@Param("title") String title, Pageable pageable);

	Page<Movie> findAll(Pageable pageable);
}
