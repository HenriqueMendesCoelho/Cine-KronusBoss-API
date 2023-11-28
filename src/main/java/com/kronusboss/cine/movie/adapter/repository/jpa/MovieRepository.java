package com.kronusboss.cine.movie.adapter.repository.jpa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.kronusboss.cine.movie.domain.Movie;
import com.kronusboss.cine.movie.domain.MovieGenre;
import com.kronusboss.cine.movie.domain.MovieNote;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface MovieRepository extends JpaRepository<Movie, UUID>, JpaSpecificationExecutor<Movie> {

	@Transactional(readOnly = true)
	Movie findByTmdbId(Long tmdbId);

	@Override
	Page<Movie> findAll(Pageable pageable);

	@Query(value = "select *, portuguese_title as portugueseTitle from movie where portuguese_title ilike all(:title) or english_title ilike all(:title) or original_title ilike all(:title)",
			nativeQuery = true)
	Page<Movie> findMovieByTitleIlikeAll(@Param("title") String[] title, Pageable pageable);

	@Query(value = "SELECT m.*, COALESCE(AVG(n.note), 0) AS average_notes FROM movie m "
			+ " LEFT JOIN movie_note n ON m.id = n.movie_id GROUP BY m.id "
			+ " ORDER BY average_notes ASC, m.portuguese_title ASC;", nativeQuery = true)
	Page<Movie> findMovieOrderByNoteAvgASC(Pageable pageable);

	@Query(value = "SELECT m.*, COALESCE(AVG(n.note), 0) AS average_notes FROM movie m "
			+ " LEFT JOIN movie_note n ON m.id = n.movie_id GROUP BY m.id "
			+ " ORDER BY average_notes DESC, m.portuguese_title ASC;", nativeQuery = true)
	Page<Movie> findMovieOrderByNoteAvgDESC(Pageable pageable);

	default Page<Movie> findMovieFilteredCustom(List<String> titles, List<String> genres, String sortJoin,
			Pageable pageable) {
		Pageable _pageable = pageable;
		if (StringUtils.isNotEmpty(sortJoin)
				&& ("notes,asc".equalsIgnoreCase(sortJoin) || "notes,desc".equalsIgnoreCase(sortJoin))) {
			_pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		}

		return findAll(createSpecification(titles, genres, sortJoin, pageable), _pageable);
	}

	private Specification<Movie> createSpecification(List<String> titles, List<String> genres, String sortJoin,
			Pageable pageable) {
		return (Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.conjunction();
			Sort defaultSort = pageable.getSort();
			List<org.springframework.data.domain.Sort.Order> defaultOrders = defaultSort != null ? defaultSort.toList()
					: Collections.emptyList();

			if (CollectionUtils.isNotEmpty(titles)) {
				List<Predicate> titlePredicates = new ArrayList<>();
				for (String title : titles) {
					if (title != null && !title.isEmpty()) {
						titlePredicates.add(criteriaBuilder.or(
								criteriaBuilder.like(criteriaBuilder.lower(root.get("portugueseTitle")),
										"%" + title.toLowerCase() + "%"),
								criteriaBuilder.like(criteriaBuilder.lower(root.get("englishTitle")),
										"%" + title.toLowerCase() + "%"),
								criteriaBuilder.like(criteriaBuilder.lower(root.get("originalTitle")),
										"%" + title.toLowerCase() + "%")));
					}
				}

				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.and(titlePredicates.toArray(new Predicate[0])));
			}

			if (StringUtils.isNotEmpty(sortJoin) || CollectionUtils.isNotEmpty(genres)) {
				query.groupBy(root.get("id"));
			}

			if (CollectionUtils.isNotEmpty(genres)) {
				Join<Movie, MovieGenre> movieGenreJoin = root.join("genres", JoinType.LEFT);

				List<Predicate> genrePredicates = new ArrayList<>();
				for (String genreId : genres) {
					if (StringUtils.isNotEmpty(genreId)) {
						genrePredicates.add(criteriaBuilder.equal(movieGenreJoin.get("id"), Long.valueOf(genreId)));
					}
				}

				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.and(genrePredicates.toArray(new Predicate[0])));
			}

			if (StringUtils.isNotEmpty(sortJoin)) {
				Join<Movie, MovieNote> movieNoteJoin = root.join("notes", JoinType.LEFT);
				List<Order> orders = new ArrayList<>();

				if ("notes,asc".equalsIgnoreCase(sortJoin)) {
					System.err.println("Entrei1");
					orders.add(criteriaBuilder.asc(criteriaBuilder.avg(movieNoteJoin.get("note"))));
				}
				if ("notes,desc".equalsIgnoreCase(sortJoin)) {
					System.err.println("Entrei");
					orders.add(criteriaBuilder.desc(criteriaBuilder.avg(movieNoteJoin.get("note"))));
				}
				if (!defaultOrders.isEmpty()) {
					defaultOrders.forEach(order -> {
						Path<?> expression = root.get(order.getProperty());
						Order newOrder = order.isAscending() ? criteriaBuilder.asc(expression)
								: criteriaBuilder.desc(expression);
						orders.add(newOrder);
					});
				}

				query.orderBy(orders);
			}

			return predicate;
		};
	}
}
