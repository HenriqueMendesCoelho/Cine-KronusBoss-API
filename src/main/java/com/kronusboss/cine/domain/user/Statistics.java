package com.kronusboss.cine.domain.user;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user_statistics")
@NoArgsConstructor
@Data
public class Statistics {
	
	@Id
	@Column(name = "user_id")
	private UUID id;
	
	@Column
	private int ratingsGiven;
	
	@Column
	private int registeredMovies;
	
	@JsonIgnore
	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@Builder
	public Statistics(int ratingsGiven, int registeredMovies, User user) {
		this.ratingsGiven = ratingsGiven;
		this.registeredMovies = registeredMovies;
		this.user = user;
	}
	
}
