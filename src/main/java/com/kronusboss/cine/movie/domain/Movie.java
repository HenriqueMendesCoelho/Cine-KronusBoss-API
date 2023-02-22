package com.kronusboss.cine.movie.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="movies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private UUID id;

	@Column(length=150, nullable = false)
	private String portugueseTitle;
	
	@Column(length=150, nullable = false)
	private String englishTitle;

	@Column(length=150, nullable = false)
	private String originalTitle;

	@Column(length=150, nullable = false)
	private String director;

	@Column(columnDefinition = "text")
	private String urlImage;

	@Column(columnDefinition = "text")
	private String portugueseUrlTrailer;

	@Column(columnDefinition = "text")
	private String englishUrlTrailer;

	@Column(columnDefinition = "text")
	private String description;
	
	@Column
	private LocalDate releaseDate;
	
	@Column(nullable = true, unique = true)
	private Long tmdbId;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "movie")
	private List<MovieNote> notes;
	
}
