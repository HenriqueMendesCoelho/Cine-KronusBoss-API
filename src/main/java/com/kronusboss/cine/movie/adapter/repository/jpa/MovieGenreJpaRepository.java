package com.kronusboss.cine.movie.adapter.repository.jpa;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.kronusboss.cine.movie.domain.MovieGenre;

@Primary
public interface MovieGenreJpaRepository extends JpaRepository<MovieGenre, Long>, JpaSpecificationExecutor<MovieGenre> {

	@Transactional(readOnly = true)
	MovieGenre findByName(String name);

	@Transactional(readOnly = true)
	List<MovieGenre> findAllByOrderByNameAsc();
}
