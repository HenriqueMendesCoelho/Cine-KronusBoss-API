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
}
