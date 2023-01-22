package com.kronusboss.cine.domain.user;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
