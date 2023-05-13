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
	private int ratingsGiven;

	@Transient
	private int registeredMovies;

	@Column
	private int consecutiveFailedLoginAttempts;

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

	public int getRatingsGiven() {
		return user.getNotes() != null ? user.getNotes().size() : 0;
	}

	public int getRegisteredMovies() {
		return user.getMovies() != null ? user.getMovies().size() : 0;
	}

}
