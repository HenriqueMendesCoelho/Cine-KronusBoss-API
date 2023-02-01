package com.kronusboss.cine.domain.user;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_preferences")
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class Preferences {
	
	@Id
    @Column
	private UUID id;
	
	@Column
	private boolean notify;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@Builder
	public Preferences(boolean notify, User user) {
		this.notify = notify;
		this.user = user;
	}

}
