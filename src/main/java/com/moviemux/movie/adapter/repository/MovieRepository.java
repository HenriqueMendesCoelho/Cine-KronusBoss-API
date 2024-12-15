package com.moviemux.movie.adapter.repository;

import com.moviemux.movie.adapter.repository.jpa.MovieJpaRepository;
import com.moviemux.movie.adapter.repository.jpa.specification.MovieSpecification;

public interface MovieRepository extends MovieJpaRepository, MovieSpecification {

}
