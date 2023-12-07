package com.kronusboss.cine.movie.adapter.repository.jpa.specification;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.kronusboss.cine.movie.domain.Movie;

public interface MovieSpecification {

	Specification<Movie> filterMovies(List<String> titles, List<Long> genres, String sortJoin, Pageable pageable);

}
