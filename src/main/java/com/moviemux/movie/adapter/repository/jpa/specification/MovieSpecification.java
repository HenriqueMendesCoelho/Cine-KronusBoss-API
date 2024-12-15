package com.moviemux.movie.adapter.repository.jpa.specification;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.moviemux.movie.domain.Movie;

public interface MovieSpecification {

	Page<Movie> findMovieFilteredCustom(List<String> titles, List<Long> genres, String sortJoin, Pageable pageable);

}
