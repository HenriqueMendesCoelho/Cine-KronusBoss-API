package com.moviemux.movie.domain;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = { "userId", "movieId" })
public class MovieNoteKey implements Serializable {
	private static final long serialVersionUID = 3416178085752264747L;

	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "movie_id")
	private UUID movieId;

}
