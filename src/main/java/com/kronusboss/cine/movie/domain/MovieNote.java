package com.kronusboss.cine.movie.domain;

import com.kronusboss.cine.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Comparator;

@Entity
@Table(name = "movie_note")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieNote implements Serializable, Cloneable {
	private static final long serialVersionUID = -3877745486636189677L;

	@EmbeddedId
	private MovieNoteKey id;

	@Column
	private Integer note;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "timestamp with time zone")
	private OffsetDateTime createdAt;

	@UpdateTimestamp
	@Column(columnDefinition = "timestamp with time zone")
	private OffsetDateTime updatedAt;

	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@MapsId("movieId")
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@Builder
	public MovieNote(Integer note, OffsetDateTime createdAt, OffsetDateTime updatedAt, User user, Movie movie) {
		this.id = new MovieNoteKey(user.getId(), movie.getId());
		this.note = note;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.user = user;
		this.movie = movie;
	}

	public static Comparator<MovieNote> comparator() {
		return Comparator.comparingInt(MovieNote::getNoteComparator)
				.reversed()
				.thenComparing((m) -> m.getUser().getName());
	}

	public static Comparator<MovieNote> comparatorAlphabetical() {
		return Comparator.comparing((m) -> m.getUser().getName());
	}

	@Override
	public MovieNote clone() throws CloneNotSupportedException {
		return (MovieNote) super.clone();

	}

	public Integer getNoteComparator() {
		return note != null ? note : 0;
	}

}
