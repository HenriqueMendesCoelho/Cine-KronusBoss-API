package com.kronusboss.cine.user.domain;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
		return user.getNotes() != null ? user.getNotes().size() : 0;
	}

	public Integer getRegisteredMovies() {
		return user.getMovies() != null ? user.getMovies().size() : 0;
	}

	public Integer getDisplayTime() {
		return user.getMovies() != null
				? user.getNotes().stream().map(n -> n.getMovie().getRuntime()).mapToInt(m -> m).sum()
				: 0;
	}

	public Double getAverageRatingMovies() {

		return user.getMovies() != null
				? user.getNotes().stream().map(n -> n.getNote()).mapToDouble(m -> m).average().orElse(0)
				: 0;
	}

}
