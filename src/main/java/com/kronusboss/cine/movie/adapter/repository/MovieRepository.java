package com.kronusboss.cine.movie.adapter.repository;

import com.kronusboss.cine.movie.adapter.repository.jpa.MovieJpaRepository;
import com.kronusboss.cine.movie.adapter.repository.jpa.specification.MovieSpecification;

public interface MovieRepository extends MovieJpaRepository, MovieSpecification {

}
