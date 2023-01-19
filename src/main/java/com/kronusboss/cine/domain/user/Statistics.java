package com.kronusboss.cine.domain.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="user_statistics")
public class Statistics {
	
	@Id
	@Column(name = "user_id")
	private UUID id;
	
	@Column
	private int ratingsGiven = 0;
	
	@Column
	private int registeredMovies = 0;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	
	@UpdateTimestamp
	@Column
	private LocalDateTime updatedAt;
}
