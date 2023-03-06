package com.kronusboss.cine.adapter.movie.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.kronusboss.cine.movie.domain.MovieGenre;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {

	@Transactional(readOnly = true)
	MovieGenre findByName(String name);

}
