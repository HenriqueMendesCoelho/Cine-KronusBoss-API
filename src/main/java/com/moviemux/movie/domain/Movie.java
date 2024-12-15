package com.moviemux.movie.domain;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.moviemux.user.domain.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private UUID id;

	@Column(length = 150, nullable = false)
	private String portugueseTitle;

	@Column(length = 150, nullable = false)
	private String englishTitle;

	@Column(length = 150, nullable = true)
	private String originalTitle;

	@Column(length = 150, nullable = false)
	private String director;

	@Column(columnDefinition = "text")
	private String urlImage;

	@Column(columnDefinition = "text")
	private String portugueseUrlTrailer;

	@Column(columnDefinition = "text")
	private String englishUrlTrailer;

	@Column(columnDefinition = "text", nullable = false)
	private String description;

	@Column
	private LocalDate releaseDate;

	@Column(nullable = true, unique = true)
	private Long tmdbId;

	@Column(nullable = true, unique = true)
	private String imdbId;

	@Column(nullable = true)
	private Integer runtime;

	@Column
	private boolean showNotes;

	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
	private List<MovieNote> notes;

	@ManyToMany
	@JoinTable(name = "movies_genres", joinColumns = @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private List<MovieGenre> genres;

	@ManyToOne(optional = true)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
	private User user;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "timestamp with time zone")
	private OffsetDateTime createdAt;

	@UpdateTimestamp
	@Column(columnDefinition = "timestamp with time zone")
	private OffsetDateTime updatedAt;

	@OneToOne(mappedBy = "movie", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private MovieDiscord movieDiscord;
}
