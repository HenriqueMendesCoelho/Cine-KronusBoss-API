package com.kronusboss.cine.movie.adapter.repository.jpa.specification.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.kronusboss.cine.movie.adapter.repository.jpa.MovieGenreJpaRepository;
import com.kronusboss.cine.movie.adapter.repository.jpa.specification.MovieGenreSpecification;
import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.domain.MovieGenre;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

@Repository
public class MovieGenreSpecificationImpl implements MovieGenreSpecification {

	@Autowired
	private MovieGenreJpaRepository repository;

	@Override
	public List<MovieGenre> findAllGenresHaveMovieAssociated() {
		return repository.findAll(filterMovieGenreWithMovie());
	}

	private Specification<MovieGenre> filterMovieGenreWithMovie() {
		return (Root<MovieGenre> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			Join<MovieGenre, Movie> movieJoin = root.join("movies", JoinType.LEFT);
			query.orderBy(criteriaBuilder.asc(root.get("name")));

			return criteriaBuilder.and(criteriaBuilder.isNotNull(movieJoin));
		};
	}
}
