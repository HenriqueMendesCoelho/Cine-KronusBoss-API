package com.kronusboss.cine.adapter.repository.jpa.movie;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.kronusboss.cine.domain.movie.Movie;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
	
	@Transactional(readOnly = true)
	Movie findByTmdbId(Long tmdbId);
	
}
