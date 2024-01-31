package com.kronusboss.cine.movie.adapter.repository;

import com.kronusboss.cine.movie.adapter.repository.jpa.MovieGenreJpaRepository;
import com.kronusboss.cine.movie.adapter.repository.jpa.specification.MovieGenreSpecification;

public interface MovieGenreRepository extends MovieGenreJpaRepository, MovieGenreSpecification {

}
