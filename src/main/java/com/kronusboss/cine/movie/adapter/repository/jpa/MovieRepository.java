package com.kronusboss.cine.movie.adapter.repository.jpa;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.kronusboss.cine.movie.adapter.repository.jpa.specification.MovieSpecification;
import com.kronusboss.cine.movie.domain.Movie;

public interface MovieRepository
		extends JpaRepository<Movie, UUID>, JpaSpecificationExecutor<Movie>, MovieSpecification {

	@Transactional(readOnly = true)
	Movie findByTmdbId(Long tmdbId);

	@Override
	Page<Movie> findAll(Pageable pageable);

	default Page<Movie> findMovieFilteredCustom(List<String> titles, List<Long> genres, String sortJoin,
			Pageable pageable) {
		Pageable _pageable = pageable;
		if (StringUtils.isNotEmpty(sortJoin)
				&& ("notes,asc".equalsIgnoreCase(sortJoin) || "notes,desc".equalsIgnoreCase(sortJoin))) {
			_pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		}

		return findAll(filterMovies(titles, genres, sortJoin, pageable), _pageable);
	}
}
