package com.kronusboss.cine.adapter.repository.jpa.movie;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kronusboss.cine.domain.movie.Movie;

public interface MovieRepository extends JpaRepository<Movie, UUID> {

}
