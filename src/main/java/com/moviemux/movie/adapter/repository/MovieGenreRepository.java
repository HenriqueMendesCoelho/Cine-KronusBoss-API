package com.moviemux.movie.adapter.repository;

import com.moviemux.movie.adapter.repository.jpa.MovieGenreJpaRepository;
import com.moviemux.movie.adapter.repository.jpa.specification.MovieGenreSpecification;

public interface MovieGenreRepository extends MovieGenreJpaRepository, MovieGenreSpecification {

}
