package com.moviemux.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moviemux.movie.domain.MovieNote;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_statistics")
@NoArgsConstructor
@Data
public class Statistics {

	@Id
	@Column(name = "user_id")
	private UUID id;

	@Transient
	private Integer ratingsGiven;

	@Transient
	private Integer registeredMovies;

	@Transient
	private Integer displayTime;

	@Transient
	private Double averageRatingMovies;

	@Column
	private Integer consecutiveFailedLoginAttempts;

	@Column(columnDefinition = "timestamp with time zone")
	private OffsetDateTime lastLoginAt;

	@JsonIgnore
	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Builder
	public Statistics(int ratingsGiven, int registeredMovies, User user) {
		this.ratingsGiven = ratingsGiven;
		this.registeredMovies = registeredMovies;
		this.consecutiveFailedLoginAttempts = 0;
		this.user = user;
	}

	public Integer getRatingsGiven() {
		if (CollectionUtils.isNotEmpty(user.getNotes())) {
			return user.getNotes().size();
		}

		return 0;
	}

	public Integer getRegisteredMovies() {
		if (CollectionUtils.isNotEmpty(user.getMovies())) {
			return user.getMovies().size();
		}

		return 0;
	}

	public Integer getDisplayTime() {
		if (CollectionUtils.isNotEmpty(user.getNotes())) {
			return user.getNotes().stream().map(n -> n.getMovie().getRuntime()).mapToInt(m -> m).sum();
		}

		return 0;
	}

	public Double getAverageRatingMovies() {
		if (CollectionUtils.isNotEmpty(user.getNotes())) {
			return user.getNotes().stream().map(MovieNote::getNote).mapToDouble(m -> m).average().orElse(0);
		}

		return 0d;
	}

}
