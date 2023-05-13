package com.kronusboss.cine.movie.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.kronusboss.cine.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movie_note")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieNote implements Serializable {
	private static final long serialVersionUID = -3877745486636189677L;

	@EmbeddedId
	private MovieNoteKey id;

	@Column
	private int note;

	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column
	private LocalDateTime updatedAt;

	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@MapsId("movieId")
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@Builder
	public MovieNote(int note, LocalDateTime createdAt, LocalDateTime updatedAt, User user, Movie movie) {
		this.id = new MovieNoteKey(user.getId(), movie.getId());
		this.note = note;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.user = user;
		this.movie = movie;
	}
}
