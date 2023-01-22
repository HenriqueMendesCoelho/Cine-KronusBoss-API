package com.kronusboss.cine.domain.movie;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="movies")
@Data
@Builder
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private UUID id;
	
	@NotBlank
	@Column(length=150, nullable = false)
	private String portugueseTitle;
	
	@NotBlank
	@Column(length=150, nullable = false)
	private String englishTitle;
	
	@NotBlank
	@Column(length=150, nullable = false)
	private String director;
	
	@NotBlank
	@Column(columnDefinition = "text")
	private String urlImage;
	
	@NotBlank
	@Column(columnDefinition = "text")
	private String portugueseUrlTrailer;
	
	@NotBlank
	@Column(columnDefinition = "text")
	private String englishUrlTrailer;
	
	@NotBlank
	@Column(columnDefinition = "text")
	private String description;
	
	@Column(columnDefinition = "numeric(4)")
	private int year;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "movie")
	private List<MovieNote> notes;
}
