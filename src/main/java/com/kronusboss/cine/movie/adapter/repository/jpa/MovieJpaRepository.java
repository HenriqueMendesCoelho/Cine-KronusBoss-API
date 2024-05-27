package com.kronusboss.cine.movie.adapter.repository.jpa;

import com.kronusboss.cine.movie.domain.Movie;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Primary
public interface MovieJpaRepository extends JpaRepository<Movie, UUID>, JpaSpecificationExecutor<Movie> {

	@Transactional(readOnly = true)
	Movie findByTmdbId(Long tmdbId);

	Page<Movie> findAll(Pageable pageable);

}
